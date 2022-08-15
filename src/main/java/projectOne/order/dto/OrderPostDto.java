package projectOne.order.dto;

import lombok.Getter;
import projectOne.member.entity.Member;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@Getter
public class OrderPostDto {

    @Positive
    private long memberId;

    @Valid
    private List<OrderProductPostDto> orderProducts;

    public Member getMember() {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
