package tartaros.activityservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormQuestion {
    @JsonProperty("title")
    String question;
    String questionId;

    @JsonProperty("questionItem")
    private void unpackNameFromNestedObject(QuestionItem questionItem) {
        questionId = questionItem.getQuestion().getQuestionId();
    }

    @Getter
    private static class QuestionItem {
        private Question question;
    }

    @Getter
    private static class Question {
        private String questionId;
    }
}
