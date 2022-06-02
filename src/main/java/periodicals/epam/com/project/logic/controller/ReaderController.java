package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
import periodicals.epam.com.project.logic.entity.Reader;
import periodicals.epam.com.project.logic.entity.dto.ReaderCreateDTO;
import periodicals.epam.com.project.logic.services.ReaderService;


@AllArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    private final QueryParameterHandler queryParameterHandler;


    public ModelAndView createReader(HttpServletRequest request) {
        ReaderCreateDTO dto = queryParameterHandler.handleRequest(request, ReaderCreateDTO.class);
        Reader createdReader = readerService.createReader(dto);
        ModelAndView modelAndView = ModelAndView.withView("/reader/readerHome.jsp");
        modelAndView.setRedirect(true);
        return modelAndView;
    }
}
