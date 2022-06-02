package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
import periodicals.epam.com.project.logic.entity.Periodical;
import periodicals.epam.com.project.logic.services.PeriodicalService;

import java.util.List;


@AllArgsConstructor
public class PeriodicalController {
    private final PeriodicalService periodicalService;
    private final QueryParameterHandler queryParameterHandler;


    public ModelAndView getAllPeriodical(HttpServletRequest request) {
        List<Periodical> allPeriodical = periodicalService.getAllPeriodical();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        modelAndView.addAttribute("periodicals", allPeriodical);

        return modelAndView;
    }

    public ModelAndView getPeriodicalsByTopic(HttpServletRequest request) {
        String topic = request.getParameter("topic");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("periodicals", periodicalService.getPeriodicalsByTopic(topic));
        modelAndView.addAttribute("topic", topic);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView findPeriodicalByName(HttpServletRequest request) {
        String name = request.getParameter("name");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("periodicals", periodicalService.getPeriodicalByName(name));
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView sortPeriodicalsByCost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        List<Periodical> sortedPeriodicals;
        if(topic.isEmpty()) {
            sortedPeriodicals = periodicalService.sortPeriodicalsByCost();
        } else {
            sortedPeriodicals = periodicalService.sortPeriodicalsByCostByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView reversedSortPeriodicalsByCost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty()){
            sortedPeriodicals= periodicalService.reversedSortPeriodicalsByCost();
        }else {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByCostByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView sortPeriodicalsByName(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty()){
            sortedPeriodicals = periodicalService.sortPeriodicalsByName();
        }else {
            sortedPeriodicals = periodicalService.sortPeriodicalsByNameByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView reversedSortPeriodicalsByName(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty()){
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByName();
        }else {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByNameByTopic(topic);
            modelAndView.addAttribute("topic",topic);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }
}
