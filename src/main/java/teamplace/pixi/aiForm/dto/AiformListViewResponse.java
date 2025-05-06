package teamplace.pixi.aiForm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamplace.pixi.aiForm.domain.FormOption;
import teamplace.pixi.aiForm.domain.FormQuestion;

import java.util.List;

@Getter
public class AiformListViewResponse {

    private final String formQTitle;
    private final int formQtype;
    private final int deviceType;
    private final List<FormOption> formOptionList;

    @Builder
    private AiformListViewResponse(String formQTitle, int formQtype, int deviceType, List<FormOption> formOptionList) {
        this.formQTitle = formQTitle;
        this.formQtype = formQtype;
        this.deviceType = deviceType;
        this.formOptionList = formOptionList;
    }

    public static AiformListViewResponse from(FormQuestion formQuestion, List<FormOption> optionList) {
        return AiformListViewResponse.builder()
                .formQTitle(formQuestion.getFormQTitle())
                .formQtype(formQuestion.getFormQType())
                .deviceType(formQuestion.getDeviceType())
                .formOptionList(optionList)
                .build();
    }


}
