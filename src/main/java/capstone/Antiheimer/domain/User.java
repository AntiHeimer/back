package capstone.Antiheimer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {


    @Id
    @Column(name = "user_id")
    private String uuid;

    @NotNull
    private String id;
    @NotNull
    private String pw;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Diagnosis> diagnoses = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Location> locations = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Result> results = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<UserData> userDatas = new ArrayList<>();

}
