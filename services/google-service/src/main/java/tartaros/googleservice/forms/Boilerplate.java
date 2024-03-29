package tartaros.googleservice.forms;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.forms.v1.Forms;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Boilerplate {

    private static final String APPLICATION_NAME = "tartaros-test";

    static Forms formsService;
    static Drive driveService;

    static {

        try {

            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            driveService = new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME).build();
            formsService = new Forms.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME).build();

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}