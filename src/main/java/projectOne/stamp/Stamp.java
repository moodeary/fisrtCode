package projectOne.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectOne.global.BaseEntity;
import projectOne.member.entity.Member;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stamp extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stampId;

    @Column(nullable = false)
    private int stampCount;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        if(member.getStamp() != this) {
            member.setStamp(this);
        }
    }
}
