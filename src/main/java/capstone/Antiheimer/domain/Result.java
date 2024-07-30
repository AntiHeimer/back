package capstone.Antiheimer.domain;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Date date;

    @NotNull
    private int stage;

    @NotNull
    private String explanation;

}
