package periodicals.epam.com.project.logic.services;

import lombok.RequiredArgsConstructor;
import periodicals.epam.com.project.logic.dao.PeriodicalDAO;
import periodicals.epam.com.project.logic.entity.Periodical;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PeriodicalService {
    private final PeriodicalDAO periodicalDAO;

    public List<Periodical> getAllPeriodical() {
        return periodicalDAO.getAllPeriodicals();
    }

    public List<Periodical> getPeriodicalsByTopic(String topic) {
        return periodicalDAO.getPeriodicalsByTopic(topic);
    }

    public List<Periodical> getPeriodicalByName(String name){
        return periodicalDAO.getPeriodicalByName(name);
    }

    public List<Periodical> sortPeriodicalsByCost(){
        return periodicalDAO.getAllPeriodicals().stream().sorted(Comparator.comparing(Periodical::getCost)).collect(Collectors.toList());
    }

    public List<Periodical> reversedSortPeriodicalsByCost(){
        return periodicalDAO.getAllPeriodicals().stream().sorted(Comparator.comparing(Periodical::getCost).reversed()).collect(Collectors.toList());
    }

    public List<Periodical> sortPeriodicalsByName(){
        return periodicalDAO.getAllPeriodicals().stream().sorted(Comparator.comparing(Periodical::getName)).collect(Collectors.toList());
    }

    public List<Periodical> reversedSortPeriodicalsByName(){
        return periodicalDAO.getAllPeriodicals().stream().sorted(Comparator.comparing(Periodical::getName).reversed()).collect(Collectors.toList());
    }

    public List<Periodical> sortPeriodicalsByCostByTopic(String topic) {
        return periodicalDAO.getPeriodicalsByTopic(topic).stream().sorted(Comparator.comparing(Periodical::getCost)).collect(Collectors.toList());
    }
    public List<Periodical> reversedSortPeriodicalsByCostByTopic(String topic){
        return periodicalDAO.getPeriodicalsByTopic(topic).stream().sorted(Comparator.comparing(Periodical::getCost).reversed()).collect(Collectors.toList());
    }
    public List<Periodical> sortPeriodicalsByNameByTopic(String topic){
        return periodicalDAO.getPeriodicalsByTopic(topic).stream().sorted(Comparator.comparing(Periodical::getName)).collect(Collectors.toList());
    }
    public List<Periodical> reversedSortPeriodicalsByNameByTopic(String topic){
        return periodicalDAO.getPeriodicalsByTopic(topic).stream().sorted(Comparator.comparing(Periodical::getName).reversed()).collect(Collectors.toList());
    }
}


