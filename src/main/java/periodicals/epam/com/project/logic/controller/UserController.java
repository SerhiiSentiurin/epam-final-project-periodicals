package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
import periodicals.epam.com.project.logic.entity.User;
import periodicals.epam.com.project.logic.entity.dto.UserDto;
import periodicals.epam.com.project.logic.services.UserService;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final QueryParameterHandler queryParameterHandler;

    public ModelAndView login(HttpServletRequest request) {
        UserDto userDto = queryParameterHandler.handleRequest(request, UserDto.class);
        User userByLogin = userService.getUserByLogin(userDto);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/user/welcome.jsp");
        modelAndView.setRedirect(true);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userByLogin);
        return modelAndView;
    }
}
