package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
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
        long readerId = Long.parseLong(request.getParameter("readerId"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addAttribute("reader", readerService.getReaderById(readerId));
        modelAndView.addAttribute("periodicals", periodicalService.getPeriodicalsByReaderId(readerId));
        modelAndView.setView("/reader/readerHome.jsp");
        return modelAndView;
    }

    public ModelAndView createReader(HttpServletRequest request) {
        ReaderCreateDTO dto = queryParameterHandler.handleRequest(request, ReaderCreateDTO.class);
        Reader createdReader = readerService.createReader(dto);
        ModelAndView modelAndView = ModelAndView.withView("/reader/successRegister.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }

//    public ModelAndView addSubscription(HttpServletRequest request) {
//        AccountDTO dto = queryParameterHandler.handleRequest(request, AccountDTO.class);
//        readerService.addSubscription(dto);
//        ModelAndView modelAndView = ModelAndView.withView("/periodicals/periodical/periodicalsForSubscribing?readerId=" + dto.getReaderId());
//        modelAndView.setRedirect(true);
//        return modelAndView;
//    }

}
