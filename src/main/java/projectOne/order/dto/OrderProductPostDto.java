package projectOne.order.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class OrderProductPostDto {
    @Positive
    private long productId;

    @Positive
    private int quantity;
}
