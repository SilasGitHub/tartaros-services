package tartaros.activityservice.activity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Activity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID activityId;

    private String title;

    private String description;

    @NotNull
    @Email
    private String createdBy;

    private String committee;

    @NotNull
    private Double price;

    private Integer maxParticipants;

    @NotNull
    private Instant createdAt = Instant.now();

    @NotNull
    private Instant signUpDeadline;

    @NotNull
    private Instant activityStartDate;

    private Instant activityEndDate;

    private boolean isProcessed = false;

    @NotNull
    private String externalId; // Google Forms API ID, can be found in the web address of the form (https://docs.google.com/forms/d/{id})

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Activity_VisibleQuestion", joinColumns = @JoinColumn(name = "activity_id"))
    @Column(name = "visibleQuestion", nullable = false)
    private List<String> visibleQuestions = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Activity_DeletedQuestion", joinColumns = @JoinColumn(name = "activity_id"))
    @Column(name = "deletedResponse", nullable = false)
    private List<String> deletedResponses = new ArrayList<>();
}
