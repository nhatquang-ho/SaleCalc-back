package backend.SaleCalc_back.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ProductCommandId implements Serializable {

    private Long productId;
    private Long commandId;
}
