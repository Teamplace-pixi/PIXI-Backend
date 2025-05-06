package teamplace.pixi.aiForm.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import teamplace.pixi.aiForm.domain.FormOption;

import java.util.List;

public interface FormOptionRepository extends JpaRepository<FormOption, Long>{
    List<FormOption> findByFormQId(Long formQId);
}
