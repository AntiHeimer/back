package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Move {

    @Id
    @Column(name = "move_id")
    private String uuid;

    @NotNull
    private LocalDateTime dateTime; // datetime 단위

    @NotNull
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberdata_id")
    MemberData memberData;
}
