package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Walk {

    @Id
    @Column(name = "walk_id")
    private String uuid;

    @NotNull
    private LocalDateTime dateTime; // datetime 단위

    @NotNull
    private int value;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberdata_id")
    private MemberData memberData;
}
