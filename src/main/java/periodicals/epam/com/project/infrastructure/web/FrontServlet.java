package periodicals.epam.com.project.infrastructure.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.exception.ExceptionHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class FrontServlet extends HttpServlet {
    @Getter
    private final String servletName;
    private final DispatcherRequest dispatcherRequest;
    private final ExceptionHandler exceptionHandler;
    private final ProcessorModelAndView processorModelAndView;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ModelAndView modelAndView;
        try {
            modelAndView = dispatcherRequest.processRequest(req);
        } catch (Exception exception) {
            modelAndView = exceptionHandler.handle(exception);
        }
        processorModelAndView.processModelAndView(req, resp, modelAndView, this);
    }
}
