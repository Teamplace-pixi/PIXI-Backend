package teamplace.pixi.aiForm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.aiForm.domain.FormQuestion;

import java.util.List;

public interface FormRepository extends JpaRepository<FormQuestion, Long> {
    List<FormQuestion> findByDeviceType(int deviceType);
}
