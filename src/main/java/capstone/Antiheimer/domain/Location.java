package capstone.Antiheimer.domain;

import capstone.Antiheimer.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @Column(name = "location_id")
    private String uuid;

    @NotNull
    private String encryptedLocation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private LocalDateTime date;

//    @NotNull
//    private String latitude;
//
//    @NotNull
//    private String longitude;
//
//    @NotNull
//    private LocalDateTime dateTime;
}
