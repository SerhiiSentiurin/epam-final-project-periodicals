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

        PeriodicalService periodicalService = getPeriodicalService(dataSource);
        ReaderService readerService = getReaderService(dataSource);

        PeriodicalController periodicalController = createPeriodicalController(queryParameterHandler, periodicalService);
        ReaderController readerController = createReaderController(queryParameterHandler, periodicalService, readerService);
        placeholders.add(new Placeholder("POST", "reader/create", readerController::createReader));
        placeholders.add(new Placeholder("POST", "reader/addSubscribing", readerController::addSubscription));
        placeholders.add(new Placeholder("GET", "reader", readerController::getReaderById));
        placeholders.add(new Placeholder("GET", "periodical/watch", periodicalController::getAllPeriodical));
        placeholders.add(new Placeholder("GET", "periodical/watchByTopic", periodicalController::getPeriodicalsByTopic));
        placeholders.add(new Placeholder("GET", "periodical/findByName", periodicalController::findPeriodicalByName));
        placeholders.add(new Placeholder("GET", "periodical/sortByCost", periodicalController::sortPeriodicalsByCost));
        placeholders.add(new Placeholder("GET", "periodical/reversedSortByCost", periodicalController::reversedSortPeriodicalsByCost));
        placeholders.add(new Placeholder("GET", "periodical/sortByName", periodicalController::sortPeriodicalsByName));
        placeholders.add(new Placeholder("GET", "periodical/reversedSortByName", periodicalController::reversedSortPeriodicalsByName));
        placeholders.add(new Placeholder("GET", "periodical/periodicalsForSubscribing", periodicalController::getPeriodicalsForSubscribing));
        placeholders.add(new Placeholder("GET", "periodical/readerSubscriptions", periodicalController::getPeriodicalsByReaderId));
        placeholders.add(new Placeholder("POST", "updateAccount", readerController::updateAccount));
        return new DispatcherRequest(placeholders);
    }

    private UserController createUserController(QueryParameterHandler queryParameterHandler, DataSource dataSource) {
        Map<UserRole, String> mapView = Map.of(UserRole.ADMIN, "/admin/adminHome.jsp", UserRole.READER, "/reader/readerHome.jsp");
        UserDao userDao = new UserDao(dataSource);
        UserService userService = new UserService(userDao);
        return new UserController(userService, queryParameterHandler, mapView);
    }

    private ReaderController createReaderController(QueryParameterHandler queryParameterHandler, PeriodicalService periodicalService, ReaderService readerService) {
        return new ReaderController(readerService, periodicalService, queryParameterHandler);
    }

    private PeriodicalController createPeriodicalController(QueryParameterHandler queryParameterHandler, PeriodicalService periodicalService) {
        return new PeriodicalController(periodicalService, queryParameterHandler);
    }

    private PeriodicalService getPeriodicalService(DataSource dataSource) {
        PeriodicalDAO periodicalDAO = new PeriodicalDAO(dataSource);
        return new PeriodicalService(periodicalDAO);
    }

    private ReaderService getReaderService(DataSource dataSource) {
        ReaderDAO readerDAO = new ReaderDAO(dataSource);
        return new ReaderService(readerDAO);
    }
}
