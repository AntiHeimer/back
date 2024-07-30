package capstone.Antiheimer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class UserDate {

    @Id
    @Column(name = "date_id")
    private String uuid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Date date;

    @NotNull
    private int rem;

    @NotNull
    private int core;

    @NotNull
    private int deep;

    @NotNull
    private int sleepTime;

    @NotNull
    private int move;

    @NotNull
    private int stand;

    @NotNull
    private int exercise;
}
