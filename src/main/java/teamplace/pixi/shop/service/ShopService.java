package teamplace.pixi.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.Device.repository.DeviceRepository;
import teamplace.pixi.Device.service.DeviceService;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.shop.domain.ShopReview;
import teamplace.pixi.shop.dto.AddReviewRequest;
import teamplace.pixi.shop.dto.AddShopRequest;
import teamplace.pixi.shop.dto.ShopListViewResponse;
import teamplace.pixi.shop.dto.ShopReviewListViewResponse;
import teamplace.pixi.shop.repository.ShopRepository;
import teamplace.pixi.shop.repository.ShopReviewRepository;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.user.repository.UserRepository;
import teamplace.pixi.user.service.UserService;
import teamplace.pixi.util.s3.AwsS3Service;
import teamplace.pixi.util.s3.Image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopReviewRepository shopReviewRepository;
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;
    private final DeviceService deviceService;

    //수리업체 리스트
    public List<ShopListViewResponse> getShopList(Integer type){
        List<Shop> shops = shopRepository.findByShopDevice(type);
        return shops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    private ShopListViewResponse convertToDto(Shop shop) {
        ShopListViewResponse dto = new ShopListViewResponse();
        dto.setShopId(shop.getShopId());
        dto.setShopName(shop.getShopName());
        dto.setShopLoc(shop.getShopLoc());
        dto.setShopOpenTime(shop.getShopOpenTime());
        dto.setShopDevice(shop.getShopDevice());
        dto.setThumb(shop.getThumb());
        return dto;
    }

    // 디바이스 타입에 따른 수리업체 조회
    public List<ShopListViewResponse> getShopsListByDeviceId(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 기기입니다."));

        int deviceType = device.getDeviceType();
        List<Shop> shops;

        if (deviceType == 0 || deviceType == 2 || deviceType == 3) {
            shops = shopRepository.findByShopDevice(0);
        } else if (deviceType == 1) {
            shops = shopRepository.findByShopDevice(1);
        } else {
            throw new IllegalArgumentException("지원하는 수리업체가 없는 기기입니다.");
        }

        // Shop -> ShopListViewResponse 변환
        return shops.stream()
                .map(this::convertToDto) // 또는 new ShopListViewResponse(shop)
                .collect(Collectors.toList());
    }

    //수리업체 홈 리스트
    public List<ShopListViewResponse> getShopListAtHome(){
        List<Shop> shops = shopRepository.getShopAtHome();
        return shops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    //수리업체 상세
    public Optional<Shop> getShop(Long id){
        return shopRepository.findByShopId(id);
    }

    //수리업체 후기 리스트
    public List<ShopReviewListViewResponse> getShopReviews(Long shopId){
        List<ShopReview> reviews = shopReviewRepository.findByShop_ShopId(shopId);
        return reviews.stream()
                .map(review -> new ShopReviewListViewResponse(review,deviceService.getDeviceCategory(review.getDevice().getDeviceId()) ))
                .collect(Collectors.toList());
    }

    @Transactional
    public ShopReview  createReview(AddReviewRequest addReviewRequest){
        Shop shop = findShopById(addReviewRequest.getShop_id());
        User user = userService.getCurrentUser();
        Device device = deviceService.getDeviceById(addReviewRequest.getDevice_id());

        ShopReview review = ShopReview.builder()
                .shop(shop)
                .user(user)
                .device(device)
                .reviewStar(addReviewRequest.getReviewStar())
                .reviewMoney(addReviewRequest.getReviewMoney())
                .reviewContent(addReviewRequest.getReviewContent())
                .reviewTitle(addReviewRequest.getReviewTitle())
                .reviewTime(addReviewRequest.getReviewTime())
                .createdAt(LocalDateTime.now())
                .build();

        return shopReviewRepository.save(review);
    }


    @Transactional
    public Shop save(AddShopRequest request, MultipartFile certificationFile, MultipartFile thumbFile) {
        User user = userService.getCurrentUser();
        Image certificationImage = awsS3Service.uploadSingleFile(certificationFile);
        Image thumbImage = awsS3Service.uploadSingleFile(thumbFile);

        request.setShopCertification(certificationImage.getFileUrl());
        request.setThumb(thumbImage.getFileUrl());

        Shop shop = createShop(request, user);
        Shop savedShop = shopRepository.save(shop);

        if (user.getRollId() == 0) {
            user.updateRollId(1);
            userRepository.save(user);
        }

        return savedShop;
    }

    private Shop createShop(AddShopRequest request, User user) {
        return Shop.builder()
                .userId(user.getUserId())
                .shopName(request.getShopName())
                .shopLoc(request.getShopLoc())
                .shopOpenTime(request.getShopOpenTime())
                .shopCall(request.getShopCall())
                .shopDetail(request.getShopDetail())
                .shopDevice(request.getShopDevice())
                .shopCertification(request.getShopCertification())
                .thumb(request.getThumb())
                .build();
    }

    private Shop findShopById(Long shopId) {
        return shopRepository.findByShopId(shopId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 수리업체(shop)가 존재하지 않습니다."));
    }


}