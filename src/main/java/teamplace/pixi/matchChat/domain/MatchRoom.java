package teamplace.pixi.matchChat.domain;

import jakarta.persistence.*;
import lombok.*;
import teamplace.pixi.shop.domain.Shop;
import teamplace.pixi.user.domain.User;

import java.time.LocalDateTime;

@Table(name="matchRoom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MatchRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mroom_id")
    private Long mroomId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
