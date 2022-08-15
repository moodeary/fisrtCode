package projectOne.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectOne.global.BaseEntity;
import projectOne.product.entity.Product;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    public void addOrder(Order order) {
        this.order = order;
        if(!this.order.getOrderProducts().contains(this)) {
            this.order.getOrderProducts().add(this);
        }
    }

    public void addProduct(Product product) {
        this.product = product;
        if (!this.product.getOrderProducts().contains(this)) {
            this.product.addOrderProduct(this);
        }
    }

}
