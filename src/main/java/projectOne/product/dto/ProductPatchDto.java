package projectOne.product.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import projectOne.product.entity.Product;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Getter
public class ProductPatchDto {

    private long productId;

    @NotBlank
    private String productName;

    private Optional<@Range(min= 100, max= 50000) Integer> price = Optional.empty();

//     추가된 부분. 커피 상태 값을 사전에 체크하는 Custom Validator를 만들수도 있다.
    private Product.ProductStatus productStatus;

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getPrice() {
        return price.orElse(0);
    }
}
