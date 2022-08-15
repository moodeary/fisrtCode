package projectOne.product.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
public class ProductPostDto {

    @NotBlank
    private String productName;

    @Range(min = 1000, max = 100000)
    private int price;

    @NotBlank
    @Pattern(regexp = "^([A-Za-z]){4}$",
            message = "제품 코드는 3자리 영문이어야 합니다.")
    private String productCode;
}
