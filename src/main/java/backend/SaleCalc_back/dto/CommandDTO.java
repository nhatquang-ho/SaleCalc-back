package backend.SaleCalc_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime expiredAt;
    private double total;
    private double clientPaid;
    private double inChange;
    private List<ProductDTO> products;
}
