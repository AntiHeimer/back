package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MemberData {

    @Id
    @Column(name = "data_id")
    private String uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private double weight;

    @NotNull
    private LocalDate date; // 날짜 단위로 저장

    @NotNull
    private int rem;

    @NotNull
    private int core;

    @NotNull
    private int deep;

    @NotNull
    private int sleepTime;

    @OneToMany(mappedBy = "memberData")
    private List<Active> activeList = new ArrayList<>();

    @OneToMany(mappedBy = "memberData")
    private List<Walk> walkList = new ArrayList<>();

    @OneToMany(mappedBy = "memberData")
    private List<Move> moveList = new ArrayList<>();

}
