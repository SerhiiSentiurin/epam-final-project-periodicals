package periodicals.epam.com.project.logic.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import periodicals.epam.com.project.infrastructure.web.ModelAndView;
import periodicals.epam.com.project.infrastructure.web.QueryParameterHandler;
import periodicals.epam.com.project.logic.entity.Periodical;
import periodicals.epam.com.project.logic.entity.Reader;
import periodicals.epam.com.project.logic.entity.dto.PeriodicalDTO;
import periodicals.epam.com.project.logic.services.AdminService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    @Mock
    AdminService adminService;
    @Mock
    QueryParameterHandler queryParameterHandler;
    @Mock
    HttpServletRequest request;

    @InjectMocks
    AdminController adminController;

    private static final Long PERIODICAL_ID = 5L;
    private static final Long READER_ID = 1L;

    @Test
    public void getAllPeriodicalsTest() {
        Periodical periodical1 = Mockito.mock(Periodical.class);
        Periodical periodical2 = Mockito.mock(Periodical.class);
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        when(adminService.getAllPeriodicals()).thenReturn(expectedList);

        List<Periodical> resultList = adminService.getAllPeriodicals();
        assertEquals(expectedList, resultList);

        ModelAndView modelAndView = adminController.getAllPeriodicals(request);
        assertNotNull(modelAndView);
        assertEquals("/admin/managePeriodicals.jsp", modelAndView.getView());
        assertEquals(expectedList, modelAndView.getAttributes().get("periodicals"));
        assertFalse(modelAndView.isRedirect());
    }

    @Test
    public void createNewPeriodicalTest() {
        PeriodicalDTO expectedDto = Mockito.mock(PeriodicalDTO.class);
        when(queryParameterHandler.handleRequest(request, PeriodicalDTO.class)).thenReturn(expectedDto);
        when(adminService.createNewPeriodical(expectedDto)).thenReturn(true);

        PeriodicalDTO resultDto = queryParameterHandler.handleRequest(request, PeriodicalDTO.class);
        assertEquals(expectedDto, resultDto);

        ModelAndView modelAndView = adminController.createNewPeriodical(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/managePeriodicals?name=" + expectedDto.getName() + "&topic=" + expectedDto.getTopic() + "&cost=" + expectedDto.getCost() + "&description=" + expectedDto.getDescription(), modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void deletePeriodicalByPeriodicalIdTest() {
        when(request.getParameter("periodicalId")).thenReturn(String.valueOf(PERIODICAL_ID));
        when(adminService.deletePeriodicalByPeriodicalId(PERIODICAL_ID)).thenReturn(true);

        Long periodicalId = Long.parseLong(request.getParameter("periodicalId"));
        assertEquals(PERIODICAL_ID, periodicalId);
        boolean result = adminService.deletePeriodicalByPeriodicalId(periodicalId);
        assertTrue(result);

        ModelAndView modelAndView = adminController.deletePeriodicalByPeriodicalId(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/managePeriodicals?periodicalId=" + periodicalId, modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void deletePeriodicalForReadersTest(){
        when(request.getParameter("periodicalId")).thenReturn(String.valueOf(PERIODICAL_ID));
        when(adminService.deletePeriodicalForReaders(PERIODICAL_ID)).thenReturn(true);

        Long periodicalId = Long.parseLong(request.getParameter("periodicalId"));
        assertEquals(PERIODICAL_ID, periodicalId);
        boolean result = adminService.deletePeriodicalForReaders(periodicalId);
        assertTrue(result);

        ModelAndView modelAndView = adminController.deletePeriodicalForReaders(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/managePeriodicals?periodicalId=" + periodicalId, modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void restorePeriodicalForReadersTest(){
        when(request.getParameter("periodicalId")).thenReturn(String.valueOf(PERIODICAL_ID));
        when(adminService.restorePeriodicalForReaders(PERIODICAL_ID)).thenReturn(true);

        Long periodicalId = Long.parseLong(request.getParameter("periodicalId"));
        assertEquals(PERIODICAL_ID, periodicalId);
        boolean result = adminService.restorePeriodicalForReaders(periodicalId);
        assertTrue(result);

        ModelAndView modelAndView = adminController.restorePeriodicalForReaders(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/managePeriodicals?periodicalId=" + periodicalId, modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void getPeriodicalByIdTest(){
        Periodical expectedPeriodical = Mockito.mock(Periodical.class);
        when(request.getParameter("periodicalId")).thenReturn(String.valueOf(PERIODICAL_ID));
        when(adminService.getPeriodicalById(PERIODICAL_ID)).thenReturn(expectedPeriodical);

        Long periodicalId = Long.parseLong(request.getParameter("periodicalId"));
        assertEquals(PERIODICAL_ID, periodicalId);
        Periodical resultPeriodical = adminService.getPeriodicalById(periodicalId);
        assertEquals(expectedPeriodical,resultPeriodical);

        ModelAndView modelAndView = adminController.getPeriodicalById(request);
        assertNotNull(modelAndView);
        assertEquals("/admin/editPeriodical.jsp", modelAndView.getView());
        assertEquals(expectedPeriodical,modelAndView.getAttributes().get("periodical"));
        assertFalse(modelAndView.isRedirect());
    }

    @Test
    public void editPeriodicalByIdTest(){
        PeriodicalDTO expectedDto = Mockito.mock(PeriodicalDTO.class);
        when(queryParameterHandler.handleRequest(request, PeriodicalDTO.class)).thenReturn(expectedDto);
        when(adminService.editPeriodicalById(expectedDto)).thenReturn(true);

        PeriodicalDTO resultDto = queryParameterHandler.handleRequest(request, PeriodicalDTO.class);
        assertEquals(expectedDto, resultDto);

        ModelAndView modelAndView = adminController.editPeriodicalById(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/getPeriodicalForEdit?periodicalId=" + expectedDto.getPeriodicalId(), modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void getAllReadersTest(){
        Reader reader1 = Mockito.mock(Reader.class);
        Reader reader2 = Mockito.mock(Reader.class);
        List<Reader> expectedList = new ArrayList<>();
        expectedList.add(reader1);
        expectedList.add(reader2);
        when(adminService.getAllReaders()).thenReturn(expectedList);

        List<Reader> resultList = adminService.getAllReaders();
        assertEquals(expectedList,resultList);

        ModelAndView modelAndView = adminController.getAllReaders(request);
        assertNotNull(modelAndView);
        assertEquals("/admin/manageReaders.jsp",modelAndView.getView());
        assertEquals(resultList,modelAndView.getAttributes().get("readers"));
        assertFalse(modelAndView.isRedirect());
    }

    @Test
    public void lockReaderTest(){
        when(request.getParameter("readerId")).thenReturn(String.valueOf(READER_ID));
        when(adminService.lockReader(READER_ID)).thenReturn(true);

        Long readerId = Long.parseLong(request.getParameter("readerId"));
        assertEquals(READER_ID, readerId);
        boolean result = adminService.lockReader(readerId);
        assertTrue(result);

        ModelAndView modelAndView = adminController.lockReader(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/manageReaders?readerId=" + readerId, modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }

    @Test
    public void unlockReaderTest(){
        when(request.getParameter("readerId")).thenReturn(String.valueOf(READER_ID));
        when(adminService.unlockReader(READER_ID)).thenReturn(true);

        Long readerId = Long.parseLong(request.getParameter("readerId"));
        assertEquals(READER_ID, readerId);
        boolean result = adminService.unlockReader(readerId);
        assertTrue(result);

        ModelAndView modelAndView = adminController.unlockReader(request);
        assertNotNull(modelAndView);
        assertEquals("/periodicals/admin/manageReaders?readerId=" + readerId, modelAndView.getView());
        assertTrue(modelAndView.isRedirect());
    }
}
