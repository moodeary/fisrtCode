package projectOne.order.mapper;

import org.mapstruct.Mapper;
import projectOne.member.entity.Member;
import projectOne.order.dto.OrderPatchDto;
import projectOne.order.dto.OrderPostDto;
import projectOne.order.dto.OrderProductResponseDto;
import projectOne.order.dto.OrderResponseDto;
import projectOne.order.entity.Order;
import projectOne.order.entity.OrderProduct;
import projectOne.product.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto);
    List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders);

    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
        Order order = new Order();
        Member member = new Member();
        member.setMemberId(orderPostDto.getMemberId());

        List<OrderProduct> orderProducts = orderPostDto.getOrderProducts().stream()
                .map(orderProductPostDto -> {
                    OrderProduct orderProduct = new OrderProduct();
                    Product product = new Product();
                    product.setProductId(orderProductPostDto.getProductId());
                    orderProduct.addOrder(order);
                    orderProduct.addProduct(product);
                    orderProduct.setQuantity(orderProductPostDto.getQuantity());
                    return orderProduct;
                }).collect(Collectors.toList());
        order.setMember(member);
        order.setOrderProducts(orderProducts);

        return order;
    }
    default OrderResponseDto orderToOrderResponseDto(Order order) {

        List<OrderProduct> orderCoffees = order.getOrderProducts();

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setMember(order.getMember());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        orderResponseDto.setOrderProducts(
                orderProductToOrderProductResponseDtos(orderCoffees)
        );


        return orderResponseDto;

    }
    default List<OrderProductResponseDto> orderProductToOrderProductResponseDtos(List<OrderProduct> orderProducts){

        return orderProducts
                .stream()
                .map(orderProduct -> OrderProductResponseDto
                        .builder()
                        .productId(orderProduct.getProduct().getProductId())
                        .quantity(orderProduct.getQuantity())
                        .price(orderProduct.getProduct().getPrice())
                        .productName(orderProduct.getProduct().getProductName())
                        .build())
                .collect(Collectors.toList());
    }
}