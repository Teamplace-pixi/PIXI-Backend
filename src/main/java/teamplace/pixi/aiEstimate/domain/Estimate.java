package teamplace.pixi.aiEstimate.domain;

import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamplace.pixi.user.domain.User;

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

    private int deviceTypeCode;
    private String diagnos;
    private String caution;

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL)
    private List<Part> parts;
}
