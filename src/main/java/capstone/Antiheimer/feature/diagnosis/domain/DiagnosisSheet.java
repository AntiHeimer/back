package capstone.Antiheimer.feature.diagnosis.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DiagnosisSheet {

    @Id
    private int number;

    @NotNull
    private String question;
    @NotNull
    private String direction;
    @NotNull
    private int point;

    private String answer;
}
