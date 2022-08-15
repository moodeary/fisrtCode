package projectOne.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import projectOne.product.dto.ProductPatchDto;
import projectOne.product.dto.ProductPostDto;
import projectOne.product.entity.Product;
import projectOne.product.mapper.ProductMapper;
import projectOne.product.service.ProductService;
import projectOne.dto.MultiResponseDto;
import projectOne.dto.SingleResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/product")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;

    @PostMapping
    public ResponseEntity postProduct (@Valid @RequestBody ProductPostDto productPostDto) {

        Product product = productService.createProduct(mapper.ProductPostDtoToProduct(productPostDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.ProductToResponseDto(product)), HttpStatus.CREATED);
    }


    @PatchMapping("/{product-id}")
    public ResponseEntity getProduct (@PathVariable("product-id") @Positive long productId,
                                      @Valid @RequestBody ProductPatchDto productPatchDto) {

        productPatchDto.setProductId(productId);

        Product product = productService.updateProduct(mapper.ProductPatchDtoToProduct(productPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.ProductToResponseDto(product)),HttpStatus.OK);

    }

    @GetMapping("/{product-id}")
    public ResponseEntity getProduct(@PathVariable("product-id") long productId) {
        Product product = productService.findProduct(productId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.ProductToResponseDto(product)),HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity getProducts(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size) {
        Page<Product> pageProduct = productService.findProducts(page -1, size);
        List<Product> products = pageProduct.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.ProductToResponseDtos(products),pageProduct),HttpStatus.OK);
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity deleteProduct(@PathVariable("product-id") long productId) {
        productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
