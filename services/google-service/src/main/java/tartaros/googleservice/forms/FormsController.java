package tartaros.googleservice.forms;

import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.PermissionList;
import com.google.api.services.forms.v1.FormsScopes;
import com.google.api.services.forms.v1.model.Form;
import com.google.api.services.forms.v1.model.Info;
import com.google.api.services.forms.v1.model.ListFormResponsesResponse;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

    @GetMapping("/{id}/numberOfResponses")
    private static int numberOfResponses(@PathVariable("id") String formId) throws IOException {
        String token = getAccessToken();
        ListFormResponsesResponse response = formsService.forms().responses().list(formId).setOauthToken(token).execute();
        return response.size();
    }

    @PostMapping
    private static String createNewForm() throws IOException {
        Form form = new Form();
        form.setInfo(new Info());
        form.getInfo().setTitle("New Form");

        form = formsService.forms().create(form)
                .setAccessToken(token)
                .execute();
        return form.getFormId();
    }

    @PostMapping("/{id}/publish")
    public boolean publishForm(@PathVariable("id") String formId) throws GeneralSecurityException, IOException {
        PermissionList list = driveService.permissions().list(formId).setOauthToken(token).execute();

        if (list.getPermissions().stream().filter((it) -> it.getRole().equals("reader")).findAny().isEmpty()) {
            Permission body = new Permission();
            body.setRole("reader");
            body.setType("anyone");
            driveService.permissions().create(formId, body).setOauthToken(token).execute();
            return true;
        }

        return false;
    }

    public static String getAccessToken() throws IOException {

        GoogleCredentials credential = GoogleCredentials.fromStream(Objects.requireNonNull(
                FormsController.class.getResourceAsStream("/cred.json"))).createScoped(FormsScopes.all());

        return credential.getAccessToken() != null ?
                credential.getAccessToken().getTokenValue() :
                credential.refreshAccessToken().getTokenValue();
    }

}
