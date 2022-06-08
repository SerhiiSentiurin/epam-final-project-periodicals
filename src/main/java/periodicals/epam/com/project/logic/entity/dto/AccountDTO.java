package periodicals.epam.com.project.logic.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
//    private Long id;
    private Double amountOfMoney;
    private Long readerId;
    private Long periodicalId;
}
