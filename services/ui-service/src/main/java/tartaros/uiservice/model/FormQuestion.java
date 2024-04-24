package tartaros.uiservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormQuestion implements Comparable<FormQuestion> {
    String title;
    String questionId;

    @Override
    public int compareTo(FormQuestion fq) {
        return this.questionId.compareTo(fq.questionId);
    }
}
