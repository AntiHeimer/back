package capstone.Antiheimer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DimentiaCenter {

    @Id
    @Column(name = "centerId")
    private String uuid;

    @NotNull
    private String name;
    @NotNull
    private String callNumber;
    @NotNull
    private String centerLocation;
}
