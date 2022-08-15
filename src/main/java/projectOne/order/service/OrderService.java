package projectOne.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projectOne.member.entity.Member;
import projectOne.member.service.MemberService;
import projectOne.order.entity.Order;
import projectOne.order.repository.OrderRepository;
import projectOne.product.service.ProductService;
import projectOne.exception.BusinessLogicException;
import projectOne.exception.ExceptionCode;
import projectOne.stamp.Stamp;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ProductService productService;

    /**
     * 주문 생성
     */
    public Order createOrder(Order order) {

        // 회원 확인
//        memberService.findVerifiedMember(order.getMember().getMemberId());

        // 커피가 존재하는지 확인
        System.out.println("커피 존재유무 확인");
        verifyOrder(order);

        // 주문 저장
        System.out.println("주문 저장 확인");
        Order savedOrder = saveOrder(order);

        // 스탬프 적립
        System.out.println("스탬프 적립 확인");
        updateStamp(savedOrder);



        System.out.println("savedOrder = " + savedOrder);
        // 저장
        return savedOrder;
    }

    public Order updateOrder(Order order) {

        // 주문 유무 확인
        Order findOrder = findVerifiedOrder(order.getOrderId());

        // 변경사항이 있을시에 변경
        Optional.ofNullable(order.getOrderStatus())
                .ifPresent(findOrder::setOrderStatus);


        return orderRepository.save(findOrder);
    }


    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }


    public Page<Order> findOrders(int page, int size) {

        log.info("orderService 호출 시작 ");
        return orderRepository.findAll(PageRequest.of(page,size,
                Sort.by("orderId").descending()));

    }

    // 주문 취소 ( 조건이 맞다면 )
    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();

        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }

        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        findOrder.setModifiedDate(LocalDateTime.now());

        orderRepository.save(findOrder);
    }


    private Order findVerifiedOrder(long orderId) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder =
                optionalOrder.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }

    private void verifyOrder(Order order) {
        // 회원이 존재하는지 확인
        memberService.findVerifiedMember(order.getMember().getMemberId());

        order.getOrderProducts().stream()
                .forEach(orderProduct -> productService.findVerifiedProduct(orderProduct.getProduct().getProductId()));
    }

    private void updateStamp(Order order) {

        // order에서 멤버의 아이디를 찾는다.
        Member member = memberService.findMember(order.getMember().getMemberId());

        int stampCount = calculateStampCount(order);

        Stamp stamp = member.getStamp();

        stamp.setStampCount(stamp.getStampCount() + stampCount);
        member.setStamp(stamp);

        memberService.updateMember(member);
    }

    private int calculateStampCount(Order order) {
        return order.getOrderProducts().stream()
                .map(orderProduct -> orderProduct.getQuantity())
                .mapToInt(quantity -> quantity)
                .sum();
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
