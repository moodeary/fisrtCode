package projectOne.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectOne.global.BaseEntity;
import projectOne.order.entity.Order;
import projectOne.stamp.Stamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity // JPA에서 해당 클래스를 엔티티로 인식
public class Member extends BaseEntity {

    @Id // 테이블의 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue (strategy = GenerationType.IDENTITY) 데이터베이스에서 기본키 대신 생성 / MEMBER 엔티티에는 기본키 값 할당하지 않음.
//    @GeneratedValue(strategy = GenerationType.SEQUENCE) 엔티티가 영속성 컨텍스트에 저장되기 전에 데이터베이스가 기본키에 해당하는 값을 제공
//    @GeneratedValue(strategy = GenerationType.AUTO) 데이터베이스의 Dialect(데이터베이스에 특화된 기술)에 따라 적절한 전략을 선택
    private Long memberId;

    // nullable = null값 허용, updatable = 수정 가능 여부, unique = 고유 값
    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.PERSIST)
    private Stamp stamp;


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    //    public void addOder(Order order) {
//        orders.add(order);
//    }
    public void setOrder(Order order) {
        orders.add(order);
        if (order.getMember() != this) {
            order.setMember(this);
        }
    }


    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
        if (stamp.getMember() != this) {
            stamp.setMember(this);
        }
    }


    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}