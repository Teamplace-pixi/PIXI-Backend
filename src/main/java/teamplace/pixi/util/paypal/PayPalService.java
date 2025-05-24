package teamplace.pixi.util.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final PayPalClient payPalClient;

    @Value("${RETURN_URL}")
    private String returnUrl;

    @Value("${CANCEL_URL}")
    private String cancelUrl;

    public String createPayment() {
        Payment payment = payPalClient.createPayment(
                2.12, "USD", "paypal",
                "sale",
                "구독 결제",
                cancelUrl,
                returnUrl
        );

        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return link.getHref(); // 이 URL로 리디렉션
            }
        }
        throw new IllegalStateException("승인 링크를 찾을 수 없습니다.");
    }

    public boolean executePayment(String paymentId, String payerId) {
        Payment payment = payPalClient.executePayment(paymentId, payerId);
        return "approved".equals(payment.getState());
    }
}
