package tartaros.activityservice.activity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private UUID id;

    @NotNull
    @Email
    private String createdBy;

    private String committee;

    @NotNull
    private Double price;

    private Integer maxParticipants;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private LocalDateTime signUpDeadline;

    @NotNull
    private LocalDateTime activityStartDate;

    private LocalDateTime activityEndDate;

    @NotNull
    private String externalId; // Google Forms API ID, can be found in the web address of the form (https://docs.google.com/forms/d/{id})

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Activity_VisibleQuestion", joinColumns = @JoinColumn(name = "activity_id"))
    @Column(name = "visibleQuestion", nullable = false)
    private List<String> visibileQuestions = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Activity_DeletedQuestion", joinColumns = @JoinColumn(name = "activity_id"))
    @Column(name = "deletedResponse", nullable = false)
    private List<String> deletedResponses = new ArrayList<>();
}
