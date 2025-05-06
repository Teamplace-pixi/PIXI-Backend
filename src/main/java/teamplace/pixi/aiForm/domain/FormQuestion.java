package teamplace.pixi.aiForm.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "formQuestion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "formQ_id")
    private Long formQId;

    @Column(name = "formQ_title", nullable = false)
    private String formQTitle;

    @Column(name = "formQ_type", nullable = false)
    private int formQType;

    @Column(name = "device_type", nullable = false)
    private int deviceType;

}
