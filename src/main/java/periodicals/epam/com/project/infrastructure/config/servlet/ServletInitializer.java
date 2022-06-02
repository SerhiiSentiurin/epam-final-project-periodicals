package periodicals.epam.com.project.infrastructure.config.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import lombok.extern.log4j.Log4j2;
import periodicals.epam.com.project.logic.controller.PeriodicalController;
import periodicals.epam.com.project.logic.controller.ReaderController;
import periodicals.epam.com.project.logic.controller.UserController;
import periodicals.epam.com.project.logic.dao.PeriodicalDAO;
import periodicals.epam.com.project.logic.dao.ReaderDAO;
import periodicals.epam.com.project.logic.dao.UserDao;
import periodicals.epam.com.project.infrastructure.config.ConfigLoader;
import periodicals.epam.com.project.infrastructure.config.db.ConfigureDataSource;
import periodicals.epam.com.project.infrastructure.config.db.ConfigureLiquibase;
import periodicals.epam.com.project.infrastructure.web.*;
import periodicals.epam.com.project.infrastructure.web.exception.ExceptionHandler;
import periodicals.epam.com.project.logic.entity.UserRole;
import periodicals.epam.com.project.logic.services.PeriodicalService;
import periodicals.epam.com.project.logic.services.ReaderService;
import periodicals.epam.com.project.logic.services.UserService;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Log4j2
public class ServletInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        FrontServlet frontServlet = createFrontServlet();
        ServletRegistration.Dynamic dynamic = servletContext.addServlet(frontServlet.getServletName(), frontServlet);
        dynamic.setLoadOnStartup(0);
        dynamic.addMapping("/periodicals/*");
        log.info("front servlet start up");
    }

    private FrontServlet createFrontServlet() {
        DispatcherRequest dispatcherRequest = createDispatcherRequest();
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        ProcessorModelAndView processorModelAndView = new ProcessorModelAndView();
        return new FrontServlet("frontServlet", dispatcherRequest, exceptionHandler,
                processorModelAndView);
    }

    private DispatcherRequest createDispatcherRequest() {
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.loadConfig("app.yaml");
        List<Placeholder> placeholders = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        QueryParameterHandler queryParameterHandler = new QueryParameterHandler(objectMapper);

        DataSource dataSource = new ConfigureDataSource().createDataSource(configLoader);
        ConfigureLiquibase liquibase = new ConfigureLiquibase(dataSource);
        liquibase.updateDatabase(configLoader);

        UserController userController = createUserController(queryParameterHandler, dataSource);
        placeholders.add(new Placeholder("POST", "login", userController::login));

        ReaderController readerController = createReaderController(queryParameterHandler,dataSource);
        placeholders.add(new Placeholder("POST","reader/create",readerController::createReader));

        PeriodicalController periodicalController = createPeriodicalController(queryParameterHandler,dataSource);
        placeholders.add(new Placeholder("GET","periodical/watch",periodicalController::getAllPeriodical));
        placeholders.add(new Placeholder("GET","periodical/watchByTopic",periodicalController::getPeriodicalsByTopic));
        placeholders.add(new Placeholder("GET","periodical/findByName",periodicalController::findPeriodicalByName));
        placeholders.add(new Placeholder("GET","periodical/sortByCost", periodicalController::sortPeriodicalsByCost));
        placeholders.add(new Placeholder("GET","periodical/reversedSortByCost", periodicalController::reversedSortPeriodicalsByCost));
        placeholders.add(new Placeholder("GET","periodical/sortByName", periodicalController::sortPeriodicalsByName));
        placeholders.add(new Placeholder("GET","periodical/reversedSortByName", periodicalController::reversedSortPeriodicalsByName));
        return new DispatcherRequest(placeholders);
    }

    private UserController createUserController(QueryParameterHandler queryParameterHandler, DataSource dataSource) {
        Map<UserRole, String> mapView = Map.of(UserRole.ADMIN, "/admin/adminHome.jsp", UserRole.READER, "/reader/readerHome.jsp");
        UserDao userDao = new UserDao(dataSource);
        UserService userService = new UserService(userDao);
        return new UserController(userService, queryParameterHandler, mapView);
    }

    private ReaderController createReaderController(QueryParameterHandler queryParameterHandler, DataSource dataSource){
        ReaderDAO readerDAO = new ReaderDAO(dataSource);
        ReaderService readerService = new ReaderService(readerDAO);
        return new ReaderController(readerService,queryParameterHandler);
    }

    private PeriodicalController createPeriodicalController(QueryParameterHandler queryParameterHandler, DataSource dataSource){
        PeriodicalDAO periodicalDAO = new PeriodicalDAO(dataSource);
        PeriodicalService periodicalService = new PeriodicalService(periodicalDAO);
        return new PeriodicalController(periodicalService,queryParameterHandler);
    }
}
