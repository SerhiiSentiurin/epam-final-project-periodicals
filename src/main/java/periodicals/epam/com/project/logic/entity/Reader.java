package periodicals.epam.com.project.logic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Reader extends User {
    private Account account; //cчёт
    private List<Periodical> periodicals;
    private boolean lock;
}
