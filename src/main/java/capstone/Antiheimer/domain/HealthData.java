package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class HealthData {

    @Id
    @Column(name = "data_id")
    private String uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private LocalDate date; //날짜 단위로 저장

    @OneToOne(mappedBy = "healthData")
    private Sleep sleep;

    @OneToOne(mappedBy = "healthData")
    private Active active;

    @OneToOne(mappedBy = "healthData")
    private Move move;

    @OneToOne(mappedBy = "healthData")
    private Walk walk;
}
