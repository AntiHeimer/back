package capstone.Antiheimer.feature.dementia_center.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DementiaCenter {

    @Id
    @Column(name = "center_id")
    private String uuid;

    @NotNull
    private String name;
    @NotNull
    private String callNumber;
    @NotNull
    private String centerLocation;
}
