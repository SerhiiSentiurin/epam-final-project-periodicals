package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
import periodicals.epam.com.project.logic.entity.Account;
import periodicals.epam.com.project.logic.entity.Reader;
import periodicals.epam.com.project.logic.entity.dto.AccountDTO;
import periodicals.epam.com.project.logic.entity.dto.ReaderCreateDTO;
import periodicals.epam.com.project.logic.services.PeriodicalService;
import periodicals.epam.com.project.logic.services.ReaderService;


@AllArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    private final PeriodicalService periodicalService;
    private final QueryParameterHandler queryParameterHandler;


    public ModelAndView getReaderById(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("reader", readerService.getReaderById(id));
        modelAndView.addAttribute("periodicals", periodicalService.getPeriodicalsByReaderId(id));
        modelAndView.setView("/reader/readerHome.jsp");
        return modelAndView;
    }

    public ModelAndView createReader(HttpServletRequest request) {
        ReaderCreateDTO dto = queryParameterHandler.handleRequest(request, ReaderCreateDTO.class);
        Reader createdReader = readerService.createReader(dto);
        ModelAndView modelAndView = ModelAndView.withView("/periodicals/reader?id = " + createdReader.getId());
        modelAndView.setRedirect(true);
        return modelAndView;
    }

    public ModelAndView addSubscribing(HttpServletRequest request) {
        AccountDTO dto = queryParameterHandler.handleRequest(request, AccountDTO.class);
        readerService.addSubscribing(dto);
        ModelAndView modelAndView = ModelAndView.withView("/periodicals/periodical/periodicalsForSubscribing?readerId = " + dto.getReaderId());
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}
