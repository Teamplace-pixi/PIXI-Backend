package teamplace.pixi.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import teamplace.pixi.Device.domain.Device;
import teamplace.pixi.user.domain.User;
import teamplace.pixi.util.s3.Image;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @Column(name = "board_content", nullable = false)
    private String boardContent;

    @Column(name = "board_loc", nullable = false)
    private String boardLoc;

    @Column(name = "board_cost", nullable = false)
    private int boardCost;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "board_date")
    private LocalDate boardDate;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "board_status")
    private String boardStatus;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public void addImages(List<Image> images) {
        for (Image image : images) {
            image.setBoard(this);
            this.images.add(image);
        }
    }

    @Builder
    public Board(User user, Device device, String boardTitle, String boardContent, String boardLoc, int boardCost,
                 LocalDate boardDate, String boardStatus) {
        this.user = user;
        this.device = device;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardLoc = boardLoc;
        this.boardCost = boardCost;
        this.boardDate = boardDate;
        this.boardStatus = boardStatus;
    }
}
