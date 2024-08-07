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
public class Member {


    @Id
    @Column(name = "member_id")
    private String uuid;

    @NotNull
    private String id;
    @NotNull
    private String pw;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Diagnosis> diagnoses = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Location> locations = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Result> results = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberData> userDatas = new ArrayList<>();

}
