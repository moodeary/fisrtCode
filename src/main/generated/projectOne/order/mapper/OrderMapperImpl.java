package projectOne.order.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import projectOne.order.dto.OrderPatchDto;
import projectOne.order.dto.OrderResponseDto;
import projectOne.order.entity.Order;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T15:31:27+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order orderPatchDtoToOrder(OrderPatchDto orderPatchDto) {
        if ( orderPatchDto == null ) {
            return null;
        }

        Order order = new Order();

        order.setOrderId( orderPatchDto.getOrderId() );
        order.setOrderStatus( orderPatchDto.getOrderStatus() );

        return order;
    }

    @Override
    public List<OrderResponseDto> ordersToOrderResponseDtos(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderResponseDto> list = new ArrayList<OrderResponseDto>( orders.size() );
        for ( Order order : orders ) {
            list.add( orderToOrderResponseDto( order ) );
        }

        return list;
    }
}
