package projectOne.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projectOne.product.entity.Product;
import projectOne.product.repository.ProductRepository;
import projectOne.exception.BusinessLogicException;
import projectOne.exception.ExceptionCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    /**
     * 제품 생성
     * - CODE 대문자로 일치
     * - 등록된 제품인지 확인
     * - 등록된 제품일 경우 -> 존재할 경우 throw
     *                   -> 존재하지 않으면 다음으로
     * - 대문자로 변경된 CODE 변경
     * - Return
     */
    public Product createProduct(Product product) {


        // productCode 대문자로 변경
        String productCode = product.getProductCode().toUpperCase();
        // 이미 등록된 커피인지 확인
        verifiedExistProduct(productCode);
        product.setProductCode(productCode);

        return productRepository.save(product);
    }

    /**
     *
     */
    public Product updateProduct(Product product) {

        // 조회하려는 커피가 검증된 커피인지 확인
        Product findProduct = findVerifiedProduct(product.getProductId());

        // 값 변경
        Optional.ofNullable(product.getProductName())
                .ifPresent(findProduct::setProductName);
        Optional.of(product.getPrice())
                .ifPresent(findProduct::setPrice);
        Optional.ofNullable(product.getProductCode())
                .ifPresent(findProduct::setProductCode);
        Optional.ofNullable(product.getProductStatus())
                .ifPresent(findProduct::setProductStatus);


        return productRepository.save(findProduct);
    }

    /**
     * 제품이 있는지 검증 후 리턴
     */
    public Product findProduct(long productId) {
        return findVerifiedProduct(productId);
    }



    /**
     * 제품이 있는지 검증 후 리턴
     */
    public Page<Product> findProducts(int page, int size) {

        return productRepository.findAll(PageRequest.of(page,size,
                Sort.by("productId").descending()));

    }





    /**
     * 유효한 제품인지 확인 후 제거 or 없으면 오류 출력
     */
    public void deleteProduct(long productId) {

        // 유효한 제품인지 확인
        Product product = findVerifiedProduct(productId);

        // 유효한 제품이면 제거
        productRepository.delete(product);
    }

    /**
     *
     */
    public Product findVerifiedProduct(long productId) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        return optionalProduct.orElseThrow(()-> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
    }


    /**
     * ProductId를 이용해서 제품의 유무를 Repository를 통해 확인
     * 존재하면 throw
     */
    private void verifiedExistProduct(String productCode) {
        Optional<Product> product = productRepository.findByProductCode(productCode);
        if (product.isPresent())
            throw new BusinessLogicException(ExceptionCode.PRODUCT_EXIST);

    }


}
