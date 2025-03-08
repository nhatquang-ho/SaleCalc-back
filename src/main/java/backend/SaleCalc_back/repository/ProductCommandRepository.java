package backend.SaleCalc_back.repository;

import backend.SaleCalc_back.model.ProductCommand;
import backend.SaleCalc_back.model.ProductCommandId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCommandRepository extends JpaRepository<ProductCommand, ProductCommandId> {
}
