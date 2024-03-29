package tartaros.activityservice.activity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private double price;

    private int maxParticipants;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private LocalDateTime signUpDeadline;

    @NotNull
    private LocalDateTime activityStartDate;

    private LocalDateTime activityEndDate;

    @NotNull
    private String externalId; // Google Forms API ID, can be found in the web address of the form
}
