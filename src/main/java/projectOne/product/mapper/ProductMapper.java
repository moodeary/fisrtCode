package projectOne.product.mapper;

import org.mapstruct.Mapper;
import projectOne.product.dto.ProductPatchDto;
import projectOne.product.dto.ProductPostDto;
import projectOne.product.dto.ProductResponseDto;
import projectOne.product.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product ProductPostDtoToProduct (ProductPostDto productPostDto);
    Product ProductPatchDtoToProduct (ProductPatchDto productPatchDto);
    ProductResponseDto ProductToResponseDto (Product product);
    List<ProductResponseDto> ProductToResponseDtos (List<Product> products);
}
