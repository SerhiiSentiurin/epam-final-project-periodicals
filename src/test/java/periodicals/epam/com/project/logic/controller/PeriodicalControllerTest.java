package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.logic.entity.Periodical;
import periodicals.epam.com.project.logic.services.PeriodicalService;
import periodicals.epam.com.project.logic.services.ReaderService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PeriodicalControllerTest {
    @Mock
    PeriodicalService periodicalService;
    @Mock
    ReaderService readerService;
    @Mock
    HttpServletRequest request;

    @InjectMocks
    PeriodicalController periodicalController;

    private static final Long READER_ID = 1L;
    private static final Long PERIODICAL_ID = 1L;
    private static final String TOPIC = "topic";
    private static final String NAME = "name";
    private final Periodical periodical1 = Mockito.mock(Periodical.class);
    private final Periodical periodical2 = Mockito.mock(Periodical.class);

    @Test
    public void getAllPeriodicalsTest(){
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        when(periodicalService.getAllPeriodicals()).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getAllPeriodicals();
        assertEquals(expectedList, resultList);

        ModelAndView modelAndView = periodicalController.getAllPeriodicals(request);
        assertNotNull(modelAndView);
        assertEquals("/periodical/watchPeriodical.jsp", modelAndView.getView());
        assertEquals(expectedList, modelAndView.getAttributes().get("periodicals"));
        assertFalse(modelAndView.isRedirect());
    }

    @Test
    public void getPeriodicalsByTopicTest(){
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(request.getParameter("topic")).thenReturn(TOPIC);
        when(periodicalService.getPeriodicalsByTopic(TOPIC)).thenReturn(expectedList);

        String topic = request.getParameter("topic");
        assertEquals(TOPIC,topic);

        ModelAndView modelAndView = periodicalController.getPeriodicalsByTopic(request);
        assertNotNull(modelAndView);
        assertEquals("/periodical/watchPeriodical.jsp", modelAndView.getView());
        assertEquals(expectedList,modelAndView.getAttributes().get("periodicals"));
        assertEquals(topic,modelAndView.getAttributes().get("topic"));
        assertFalse(modelAndView.isRedirect());
    }

    @Test
    public void getPeriodicalByNameTest(){
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(request.getParameter("name")).thenReturn(NAME);
        when(periodicalService.getPeriodicalByName(NAME)).thenReturn(expectedList);

        String name = request.getParameter("name");
        assertEquals(NAME,name);

        ModelAndView modelAndView = periodicalController.getPeriodicalByName(request);
        assertNotNull(modelAndView);
        assertEquals("/periodical/watchPeriodical.jsp", modelAndView.getView());
        assertEquals(expectedList,modelAndView.getAttributes().get("periodicals"));
        assertEquals(name,modelAndView.getAttributes().get("name"));
        assertFalse(modelAndView.isRedirect());
    }

    @Test
    public void sortPeriodicalsByCostTest(){

    }
}
