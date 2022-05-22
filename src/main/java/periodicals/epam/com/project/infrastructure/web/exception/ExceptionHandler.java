package periodicals.epam.com.project.infrastructure.web.exception;

import periodicals.epam.com.project.infrastructure.web.ModelAndView;

public class ExceptionHandler {
    public ModelAndView handle(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/error.jsp");
        modelAndView.addAttribute("message", exception.getMessage());
        return modelAndView;
    }
}
