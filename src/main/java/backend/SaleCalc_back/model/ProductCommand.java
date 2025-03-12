package backend.SaleCalc_back.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "productCommand")
@Data
@NoArgsConstructor
public class ProductCommand {

    @EmbeddedId
    private ProductCommandId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("commandId")
    @JoinColumn(name = "command_id")
    private Command command;

    private int quantity;
}
