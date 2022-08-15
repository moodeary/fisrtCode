package projectOne.product.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import projectOne.product.dto.ProductPatchDto;
import projectOne.product.dto.ProductPostDto;
import projectOne.product.dto.ProductResponseDto;
import projectOne.product.entity.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T15:31:27+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.15.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product ProductPostDtoToProduct(ProductPostDto productPostDto) {
        if ( productPostDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductName( productPostDto.getProductName() );
        product.setPrice( productPostDto.getPrice() );
        product.setProductCode( productPostDto.getProductCode() );

        return product;
    }

    @Override
    public Product ProductPatchDtoToProduct(ProductPatchDto productPatchDto) {
        if ( productPatchDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( productPatchDto.getProductId() );
        product.setProductName( productPatchDto.getProductName() );
        product.setPrice( productPatchDto.getPrice() );
        product.setProductStatus( productPatchDto.getProductStatus() );

        return product;
    }

    @Override
    public ProductResponseDto ProductToResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDto.ProductResponseDtoBuilder productResponseDto = ProductResponseDto.builder();

        productResponseDto.productId( product.getProductId() );
        productResponseDto.productName( product.getProductName() );
        productResponseDto.price( product.getPrice() );
        productResponseDto.productStatus( product.getProductStatus() );

        return productResponseDto.build();
    }

    @Override
    public List<ProductResponseDto> ProductToResponseDtos(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponseDto> list = new ArrayList<ProductResponseDto>( products.size() );
        for ( Product product : products ) {
            list.add( ProductToResponseDto( product ) );
        }

        return list;
    }
}
