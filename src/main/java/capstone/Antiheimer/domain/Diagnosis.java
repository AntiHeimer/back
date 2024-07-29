package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Diagnosis {

    @Id
    @Column(name = "diagnosis_id")
    private String uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private LocalDate diagnosisDate;

    @NotNull
    private int score;
}
