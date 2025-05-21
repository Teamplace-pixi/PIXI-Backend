package teamplace.pixi.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import teamplace.pixi.shop.domain.Shop;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "apply") // 실제 DB 테이블 이름
@NoArgsConstructor
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long applyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "apply_cost")
    private Integer applyCost;

    @Column(name = "apply_date")
    private LocalDateTime applyDate;  // 작업소요일

    @Column(name = "apply_content", columnDefinition = "TEXT")
    private String applyContent;

    @CreatedDate
    @Column(name = "apply_at")
    private LocalDateTime applyAt;

    @Builder
    public Apply(Board board, Shop shop, Integer applyCost, LocalDateTime applyDate, String applyContent, LocalDateTime applyAt) {
        this.board = board;
        this.shop = shop;
        this.applyCost = applyCost;
        this.applyDate = applyDate;
        this.applyContent = applyContent;
        this.applyAt = applyAt;
    }
}
