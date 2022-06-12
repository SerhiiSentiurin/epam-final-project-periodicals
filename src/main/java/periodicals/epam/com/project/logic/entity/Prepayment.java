package periodicals.epam.com.project.logic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prepayment {
    private Long id;            //primary key
    private String startDate;
    private String dueDate;
    private Long periodicalId; // foreign key to periodical
    private Long readerId; // foreign key to reader
}
