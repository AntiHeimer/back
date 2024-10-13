package capstone.Antiheimer.feature.diagnosis.entity;

import capstone.Antiheimer.feature.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Result {

    @Id
    @Column(name = "result_id")
    private String uuid;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memeber_id")
    private Member member;

    @NotNull
    private LocalDate date;

    @NotNull
    private String stage;

    @NotNull
    private String explanation;

}