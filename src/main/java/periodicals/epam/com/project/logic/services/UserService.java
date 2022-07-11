package periodicals.epam.com.project.logic.services;

import lombok.RequiredArgsConstructor;
import periodicals.epam.com.project.infrastructure.web.exception.ApplicationException;
import periodicals.epam.com.project.logic.dao.UserDAO;
import periodicals.epam.com.project.logic.entity.User;
import periodicals.epam.com.project.logic.entity.dto.UserDTO;

@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDao;

    public User getUserByLogin(UserDTO userDto) {
        User user = userDao.getUserByLogin(userDto.getLogin())
                .orElseThrow(() -> new ApplicationException("cannot find user with this login"));

        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new ApplicationException("password is incorrect");
        }
        return user;
    }
}
