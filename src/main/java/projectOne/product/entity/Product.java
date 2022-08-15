package projectOne.product.entity;

import lombok.*;
import projectOne.global.BaseEntity;
import projectOne.order.entity.OrderProduct;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    private long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, unique = true)
    private String productCode;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus productStatus = ProductStatus.PRODUCT_FOR_SALE;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addOrderProduct (OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        if (orderProduct.getProduct() != this) {
            orderProduct.addProduct(this);
        }
    }

    public enum ProductStatus {
        PRODUCT_FOR_SALE("판매중"),
        PRODUCT_SOLD_OUT("판매중지");

        @Getter
        private String status;

        ProductStatus(String status) {
            this.status = status;
        }
    }
}
