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
<<<<<<< HEAD:src/main/java/capstone/Antiheimer/feature/dementia_center/entity/DementiaCenter.java
public class DementiaCenter {
=======
public class Relation {
>>>>>>> feature/notification:src/main/java/capstone/Antiheimer/feature/relation/Relation.java

    @Id
    @Column(name = "center_id")
    private String uuid;

    @NotNull
<<<<<<< HEAD:src/main/java/capstone/Antiheimer/feature/dementia_center/entity/DementiaCenter.java
    private String name;
=======
    private String protectorId;

>>>>>>> feature/notification:src/main/java/capstone/Antiheimer/feature/relation/Relation.java
    @NotNull
    private String callNumber;
    @NotNull
    private String centerLocation;
}
