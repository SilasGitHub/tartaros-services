package tartaros.uiservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormAnswer implements Comparable<FormAnswer> {
    private String questionId;
    private String answer;

    @Override
    public int compareTo(FormAnswer fa) {
        return this.questionId.compareTo(fa.questionId);
    }
}
