package capstone.Antiheimer.feature.member.entity;

import capstone.Antiheimer.feature.diagnosis.entity.Diagnosis;
import capstone.Antiheimer.feature.diagnosis.entity.Result;
import capstone.Antiheimer.feature.health_data.entity.HealthData;
import capstone.Antiheimer.feature.location.entity.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @NotNull
    private String gender;
    @NotNull
    private LocalDate birth;

    private double weight;

    @OneToMany(mappedBy = "member")
    private List<Diagnosis> diagnosisList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Location> locationList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<HealthData> healthDataList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Result> resultList = new ArrayList<>();
}
