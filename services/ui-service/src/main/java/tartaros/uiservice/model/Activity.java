package tartaros.uiservice.model;

import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Activity {
    private UUID activityId;

    private String title;

    private String description;

    private String createdBy;

    private String committee;

    private Double price;

    private Integer maxParticipants;

    private Instant createdAt = Instant.now();

    private Instant signUpDeadline;

    private Instant activityStartDate;

    private Instant activityEndDate;

    private boolean isProcessed = false;

    private String externalId; // Google Forms API ID, can be found in the web address of the form (https://docs.google.com/forms/d/{id})

    private List<String> visibleQuestions = new ArrayList<>();

    private List<String> deletedResponses = new ArrayList<>();
}
