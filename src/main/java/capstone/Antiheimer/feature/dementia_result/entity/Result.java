package capstone.Antiheimer.feature.dementia_result.entity;

import capstone.Antiheimer.feature.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    private Date date;

    @NotNull
    private int stage;

    @NotNull
    private String explanation;

}