package capstone.Antiheimer.feature.relation.entity;

import capstone.Antiheimer.feature.member.repository.MemberRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Relation {

    @Id
    @Column(name = "relation_id")
    private String uuid;

    @NotNull
    private String guardianId;

    @NotNull
    private String wardId;

    public void setUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
