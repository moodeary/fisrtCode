package projectOne.order.entity;

import lombok.*;
import projectOne.global.BaseEntity;
import projectOne.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name= "ORDERS")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {

    public Order(long orderNumber, OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order( OrderStatus orderStatus, Member member) {
        this.orderStatus = orderStatus;
        this.member = member;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID") // 외래키에 해당하는 컬럼명을 적어준다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addMember(Member member) {
        this.member = member;
    }

    public void addOrderCoffee(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        if (orderProduct.getOrder() != this) {
            orderProduct.addOrder(this);
        }
    }
    public enum OrderStatus {
        ORDER_REQUEST(1,"주문 요청"),
        ORDER_CONFIRM(2,"주문 확정"),
        ORDER_COMPLETE(3,"주문 완료"),
        ORDER_CANCEL(4,"주문 취소");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
