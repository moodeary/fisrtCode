package projectOne.order.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import projectOne.member.entity.Member;
import projectOne.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {


    private long orderId;

    private long memberId;
    private Order.OrderStatus orderStatus;
    private List<OrderProductResponseDto> orderProducts;

    public void setMember(Member member) {
        this.memberId = member.getMemberId();
    }
}
