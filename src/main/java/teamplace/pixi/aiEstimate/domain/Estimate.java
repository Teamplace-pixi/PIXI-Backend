package teamplace.pixi.aiEstimate.domain;

import jakarta.persistence.*;
import lombok.*;
import teamplace.pixi.aiEstimate.domain.Part;
import teamplace.pixi.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estimate")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String estimatedCost;
    private String repairMethod;
    private String caution;

    private LocalDateTime createdAt;

    @Setter
    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL)
    private List<Part> parts;
}
