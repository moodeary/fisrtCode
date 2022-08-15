package projectOne.basic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projectOne.member.entity.Member;
import projectOne.order.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Configuration
public class JpaBasicConfig {

    private EntityManager em;
    private EntityTransaction tx;

    @Bean
    public CommandLineRunner testJpaBasicRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();

        //(1) entityManager를 통해 tx 객체를 얻고 jpa에서는 transaction 객체를 기준으로 데이터베이스 테이블에 데이터를 저장
        this.tx = em.getTransaction();

        return args -> {

//            test1();
//            test2();
//            test3();
//            testEmailNotNull();   // (1)
//            testEmailUpdatable(); // (2)
//            testEmailUnique();    // (3)
//            mappingManyToOneUniDirection();
//            mappingManyToOneBiDirection();

        };
    }
    /**
    private void test1() {
        //(2) Transaction 시작할때 호출!
        tx.begin();
        Member member = new Member("hbs0227@daum.net");

        //(3) member 객체를 영속성 컨텍스트에 저장
        em.persist(member);

        //(4) commit 호출하는 시점에 영속성 컨텍스트에 저장되어 있는 member 객체를 데이터베이스에 저장
        tx.commit();

        //(5) 영속성 컨텍스트에 저장한 객체를 1차 캐시에서 조회하고, 없을 시에는 select 쿼리를 전송
        Member resultMember = em.find(Member.class, 1L);
        System.out.println("Id:" + resultMember.getMemberId() + ",email: " + resultMember.getEmail());

        //(6) 객체가 존재하지 않으므로, 테이블에 직접 쿼리 전송
        Member resultMember2 = em.find(Member.class, 2L);
    }

    private void test2() {
        tx.begin();
        em.persist(new

                Member("hgd1@gmail.com"));    // (1) 1차캐시, 쓰기 지연 SQL 저장소에 저장
        tx.commit();    // (2) SQL저장소에 등록된 INSERT 쿼리 실행

        tx.begin();
        Member member1 = em.find(Member.class, 2L);  // (3) 1차 캐시에서 조회
        System.out.println("Id:" + member1.getMemberId() + ",email: " + member1.getEmail());
        member1.setEmail("hgd1@yahoo.co.kr");       // (4) Setter 메서드로 값 변경!?
        tx.commit();   // (5) 변경 후 commit하면 쓰기 지연 SQL 저장소에 등록된 UPDATE 쿼리 실행

        //-> 영속성 컨텍스트는 엔티티가 저장될 때 스냅샷을 가지고 있는데 commit 했을 떄 변경된 값이 있다면 UPDATE 쿼리가 실행된다

    }

    private void test3() {

        tx.begin();
        em.persist(new

                Member("hgd1@gmail.com"));  // (1) 1차캐시, 쓰기 지연 SQL 저장소에 저장
        tx.commit();    //(2) SQL저장소에 등록된 INSERT 쿼리 실행

        tx.begin();
        Member member2 = em.find(Member.class, 3L);   // (3) 1차 캐시에서 조회
        System.out.println("Id:" + member2.getMemberId() + ",email: " + member2.getEmail());
        em.remove(member2);     // (4) 1차 캐시에 있는 엔티티 제거를 요청
        tx.commit();     // (5) 1차 캐시에 엔티티를 제거하고, 쓰기지연 SQL 저장소에 등록된 DELETE 쿼리를 실행


    }

    private void testEmailNotNull() {
        tx.begin();
        em.persist(new Member());
        tx.commit();
    }

    private void testEmailUpdatable() {
        tx.begin();
        em.persist(new Member("hgd@gmail.com"));
        Member member = em.find(Member.class, 1L);
        member.setEmail("hgd@hanmail.net");
        tx.commit();
    }

    private void testEmailUnique() {
        tx.begin();
        em.persist(new Member("hbs@daum.net"));
        em.persist(new Member("hbs@daum.net"));
        tx.commit();
    }

    private void mappingManyToOneUniDirection() {
        tx.begin();
        Member member = new Member("hbs0227@daum.net","황병수","010-7177-0677");
        em.persist(member);

        Order order = new Order(3, Order.OrderStatus.ORDER_REQUEST,member);
//        order.addMember(member);  // 멤버객체 추가(외래키처럼?)
        em.persist(order);  // 주문정보 저장

        tx.commit();

        Order findOrder = em.find(Order.class, 1L); // 주문 정보 조회

        System.out.println("findOrder.getMember().getMemberId() = " + findOrder.getMember().getMemberId());
        // 객체를 통해 다른 객체의 정보를 얻는것을 객체 그래프 탐색이라고 한다.
     }


    private void mappingManyToOneBiDirection() {
        tx.begin();

        Member member = new Member("hbs0227@daum.net","황병수","010-7177-0677");
        Order order = new Order();

        member.addOder(order);
        order.addMember(member);

        em.persist(member);
        em.persist(order);

        tx.commit();

        Member findMember = em.find(Member.class,1L);
        Order findOrder = em.find(Order.class,1L);

        findMember.getOrders()
                .stream()
                .forEach(findOrder1-> {
                    System.out.println("findMember.getOrder.findOrder = " + findOrder1.getOrderId() + ", " + findOrder1.getOrderStatus());
                });

        System.out.println("findOrder.getMember().getMemberId() = " + findOrder.getMember().getMemberId());
    }
     */

}
