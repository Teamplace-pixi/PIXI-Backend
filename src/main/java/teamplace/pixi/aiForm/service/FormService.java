package teamplace.pixi.aiForm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamplace.pixi.aiForm.domain.FormOption;
import teamplace.pixi.aiForm.domain.FormQuestion;
import teamplace.pixi.aiForm.dto.AiformListViewResponse;
import teamplace.pixi.aiForm.repository.FormOptionRepository;
import teamplace.pixi.aiForm.repository.FormRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FormService {
    private final FormRepository formRepository;
    private final FormOptionRepository formOptionRepository;


    public List<AiformListViewResponse> getFormList(int deviceType){
        List<FormQuestion> questions= formRepository.findByDeviceType(deviceType);
        return questions.stream()
                .map(q -> {
                    List<FormOption> options = formOptionRepository.findByFormQId(q.getFormQId());
                    return AiformListViewResponse.from(q, options);
                })
                .collect(Collectors.toList());
    }

}


