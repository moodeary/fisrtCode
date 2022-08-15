package projectOne.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectOne.product.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByProductCode(String ProductCode);


}
