package projectOne.product.dto;

import lombok.Builder;
import lombok.Getter;
import projectOne.product.entity.Product;

@Builder
@Getter
public class ProductResponseDto {

    private long productId;

    private String productName;

    private int price;
    private Product.ProductStatus productStatus;

    public Product.ProductStatus getProductStatus() {
        return productStatus;
    }
}
