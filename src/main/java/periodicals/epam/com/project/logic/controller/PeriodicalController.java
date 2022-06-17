package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
import periodicals.epam.com.project.logic.entity.Periodical;
import periodicals.epam.com.project.logic.entity.Prepayment;
import periodicals.epam.com.project.logic.services.PeriodicalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        modelAndView.addAttribute("name", name);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView sortPeriodicalsByCost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        String name = request.getParameter("name");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty() && name.isEmpty()) {
            sortedPeriodicals = periodicalService.sortPeriodicalsByCost();
        } else if (name.isEmpty()) {
            sortedPeriodicals = periodicalService.sortPeriodicalsByCostByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        } else {
            sortedPeriodicals = periodicalService.sortPeriodicalsByCostByName(name);
            modelAndView.addAttribute("name", name);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView reversedSortPeriodicalsByCost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        String name = request.getParameter("name");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty() && name.isEmpty()) {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByCost();
        } else if (name.isEmpty()) {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByCostByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        } else {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByCostByName(name);
            modelAndView.addAttribute("name", name);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView sortPeriodicalsByName(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        String name = request.getParameter("name");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty()&&name.isEmpty()) {
            sortedPeriodicals = periodicalService.sortPeriodicalsByName();
        } else if (name.isEmpty()){
            sortedPeriodicals = periodicalService.sortPeriodicalsByNameByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        }else {
            sortedPeriodicals = periodicalService.sortPeriodicalsByNameByName(name);
            modelAndView.addAttribute("name", name);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView reversedSortPeriodicalsByName(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String topic = request.getParameter("topic");
        String name = request.getParameter("name");
        List<Periodical> sortedPeriodicals;
        if (topic.isEmpty()&&name.isEmpty()) {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByName();
        } else if (name.isEmpty()){
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByNameByTopic(topic);
            modelAndView.addAttribute("topic", topic);
        }else {
            sortedPeriodicals = periodicalService.reversedSortPeriodicalsByNameByName(name);
            modelAndView.addAttribute("name", name);
        }
        modelAndView.addAttribute("periodicals", sortedPeriodicals);
        modelAndView.setView("/periodical/watchPeriodical.jsp");
        return modelAndView;
    }

    public ModelAndView getPeriodicalsByReaderId(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        long readerId = Long.parseLong(request.getParameter("readerId"));
        List<Periodical> subscribedPeriodicals = periodicalService.getPeriodicalsByReaderId(readerId);
        List<Prepayment> prepaymentInfo = periodicalService.getPrepaymentInfoByReaderId(readerId);
        Map<List<Periodical>, List<Prepayment>> infoList = new HashMap<>();
        infoList.put(subscribedPeriodicals,prepaymentInfo);
        modelAndView.addAttribute("periodicals", infoList);
//        modelAndView.addAttribute("periodicals", subscribedPeriodicals);
//        modelAndView.addAttribute("prepayments", prepaymentInfo);
        modelAndView.setView("/periodical/watchSubscriptions.jsp");
        return modelAndView;
    }

    public ModelAndView getPeriodicalsForSubscribing(HttpServletRequest request) {
        long readerId = Long.parseLong(request.getParameter("readerId"));
        List<Periodical> periodicalsForSubscribing = periodicalService.getPeriodicalsForSubscribing(readerId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("periodicals", periodicalsForSubscribing);
        modelAndView.setView("/periodical/periodicalsForSubscribing.jsp");
        return modelAndView;
    }
}
