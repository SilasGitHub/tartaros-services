package tartaros.googleservice.forms;

import com.google.api.services.drive.model.Permission;
import com.google.api.services.forms.v1.FormsScopes;
import com.google.api.services.forms.v1.model.*;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static tartaros.googleservice.forms.Boilerplate.driveService;
import static tartaros.googleservice.forms.Boilerplate.formsService;

@RestController
@RequestMapping("google/forms")
public class FormsController {
    private static final String token;

    static {
        try {
            token = getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/responses")
    private static Iterable<FormResponse> getResponses(@PathVariable("id") String formId) throws IOException {
        ListFormResponsesResponse response = formsService.forms().responses().list(formId).setOauthToken(token).execute();

        return response.getResponses();
    }

    @GetMapping("/{activityId}/response/{responseId}")
    private static FormResponse getResponse(@PathVariable("activityId") String formId, @PathVariable("responseId") String responseId) throws IOException {
        return formsService.forms().responses().get(formId, responseId).setOauthToken(token).execute();
    }

    @GetMapping("/{id}/questions")
    private static Iterable<Item> getQuestions(@PathVariable("id") String formId) throws IOException {
        Form form = formsService.forms().get(formId).setAccessToken(token).execute();
        List<Item> items = form.getItems();
        if (items == null) return new ArrayList<>();
        return items.stream().filter((it) -> it.getQuestionItem() != null && !it.getQuestionItem().isEmpty()).toList();
    }

    @PostMapping("/{createdBy}")
    private static String createNewForm(@PathVariable("createdBy") String createdBy) throws IOException {
        Form form = new Form();
        form.setInfo(new Info());
        form.getInfo().setTitle("New Form");

        // Create the form
        form = formsService.forms().create(form)
                .setAccessToken(token)
                .execute();

        // Allow the creator to edit the form, anyone is allowed to view it by default
        Permission permission = new Permission().setRole("writer").setType("user").setEmailAddress(createdBy);
        driveService.permissions()
                .create(form.getFormId(), permission)
                .setOauthToken(token)
                .execute();

        return form.getFormId();
    }

    public static String getAccessToken() throws IOException {

        GoogleCredentials credential = GoogleCredentials.fromStream(Objects.requireNonNull(
                FormsController.class.getResourceAsStream("/cred.json"))).createScoped(FormsScopes.all());

        return credential.getAccessToken() != null ?
                credential.getAccessToken().getTokenValue() :
                credential.refreshAccessToken().getTokenValue();
    }

}
