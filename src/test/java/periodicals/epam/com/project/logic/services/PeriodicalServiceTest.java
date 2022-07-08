package periodicals.epam.com.project.logic.services;

import liquibase.pro.packaged.M;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import periodicals.epam.com.project.logic.dao.PeriodicalDAO;
import periodicals.epam.com.project.logic.entity.Periodical;
import periodicals.epam.com.project.logic.entity.Prepayment;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PeriodicalServiceTest {
    @Mock
    PeriodicalDAO dao;

    @InjectMocks
    PeriodicalService periodicalService;

    private static final Long PERIODICAL_ID = 1L;
    private static final Long READER_ID = 1L;
    private static final String NAME = "name";
    private static final String TOPIC = "topic";
    private final Periodical periodical1 = new Periodical(PERIODICAL_ID, "name3", "topic3", 40d, "description3", false);
    private final Periodical periodical2 = new Periodical(2L, "name1", "topic1", 20d, "description1", false);
    private final Periodical periodical3 = new Periodical(3L, "name2", "topic2", 30d, "description2", false);
    private final Prepayment prepayment1 = new Prepayment(1L, "startDate1", "dueDate1", PERIODICAL_ID, READER_ID);
    private final Prepayment prepayment2 = new Prepayment(2L, "startDate2", "dueDate2", 2L, 2L);


    @Test
    public void getAllPeriodicalsTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(dao.getAllPeriodicals()).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getAllPeriodicals();
        assertEquals(expectedList, resultList);
    }

    @Test
    public void getPeriodicalsByTopicTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(dao.getPeriodicalsByTopic(TOPIC)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getPeriodicalsByTopic(TOPIC);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void getPeriodicalsByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(dao.getPeriodicalByName(NAME)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getPeriodicalByName(NAME);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void sortPeriodicalsByCostTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getCost)).collect(Collectors.toList());

        when(periodicalService.sortPeriodicalsByCost()).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.sortPeriodicalsByCost();
        assertEquals(expectedList, resultList);
    }

    @Test
    public void reversedSortPeriodicalsByCostTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getCost).reversed()).collect(Collectors.toList());

        when(periodicalService.reversedSortPeriodicalsByCost()).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.reversedSortPeriodicalsByCost();
        assertEquals(expectedList, resultList);
    }

    @Test
    public void sortPeriodicalsByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getName)).collect(Collectors.toList());

        when(periodicalService.sortPeriodicalsByName()).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.sortPeriodicalsByName();
        assertEquals(expectedList, resultList);
    }

    @Test
    public void reversedSortPeriodicalsByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getName).reversed()).collect(Collectors.toList());

        when(periodicalService.reversedSortPeriodicalsByName()).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.reversedSortPeriodicalsByName();
        assertEquals(expectedList, resultList);
    }

    @Test
    public void sortPeriodicalsByCostByTopicTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getCost)).collect(Collectors.toList());

        when(periodicalService.sortPeriodicalsByCostByTopic(TOPIC)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.sortPeriodicalsByCostByTopic(TOPIC);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void reversedSortPeriodicalsByCostByTopicTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getCost).reversed()).collect(Collectors.toList());

        when(periodicalService.reversedSortPeriodicalsByCostByTopic(TOPIC)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.reversedSortPeriodicalsByCostByTopic(TOPIC);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void sortPeriodicalsByNameByTopicTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getName)).collect(Collectors.toList());

        when(periodicalService.sortPeriodicalsByNameByTopic(TOPIC)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.sortPeriodicalsByNameByTopic(TOPIC);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void reversedSortPeriodicalsByNameByTopicTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getName).reversed()).collect(Collectors.toList());

        when(periodicalService.reversedSortPeriodicalsByNameByTopic(TOPIC)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.reversedSortPeriodicalsByNameByTopic(TOPIC);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void sortPeriodicalsByCostByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getCost)).collect(Collectors.toList());

        when(periodicalService.sortPeriodicalsByCostByName(NAME)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.sortPeriodicalsByCostByName(NAME);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void reversedSortPeriodicalsByCostByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getCost).reversed()).collect(Collectors.toList());

        when(periodicalService.reversedSortPeriodicalsByCostByName(NAME)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.reversedSortPeriodicalsByCostByName(NAME);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void sortPeriodicalsByNameByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getName)).collect(Collectors.toList());

        when(periodicalService.sortPeriodicalsByNameByName(NAME)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.sortPeriodicalsByNameByName(NAME);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void reversedSortPeriodicalsByNameByNameTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);
        expectedList = expectedList.stream().sorted(Comparator.comparing(Periodical::getName).reversed()).collect(Collectors.toList());

        when(periodicalService.reversedSortPeriodicalsByNameByName(NAME)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.reversedSortPeriodicalsByNameByName(NAME);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void getPeriodicalsByReaderIdTest() {
        List<Periodical> expectedList = new ArrayList<>();
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList.add(periodical3);

        when(dao.getPeriodicalsByReaderId(READER_ID)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getPeriodicalsByReaderId(READER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void getPeriodicalsByTopicByReaderIdTest() {
        Map<Periodical, Prepayment> expectedMap = new HashMap<>();
        expectedMap.put(periodical1, prepayment1);
        expectedMap.put(periodical2, prepayment2);

        when(dao.getPeriodicalsByTopicByReaderId(TOPIC, READER_ID)).thenReturn(expectedMap);

        Map<Periodical, Prepayment> resultMap = periodicalService.getPeriodicalsByTopicByReaderId(TOPIC, READER_ID);
        assertEquals(expectedMap, resultMap);
    }

    @Test
    public void findPeriodicalsByNameByReaderIdTest() {
        Map<Periodical, Prepayment> expectedMap = new HashMap<>();
        expectedMap.put(periodical1, prepayment1);
        expectedMap.put(periodical2, prepayment2);

        when(dao.findPeriodicalsByNameByReaderId(NAME, READER_ID)).thenReturn(expectedMap);

        Map<Periodical, Prepayment> resultMap = periodicalService.findPeriodicalsByNameByReaderId(NAME, READER_ID);
        assertEquals(expectedMap, resultMap);
    }

    @Test
    public void getPrepaymentsByReaderIdTest() {
        List<Prepayment> expectedList = new ArrayList<>();
        expectedList.add(prepayment1);
        expectedList.add(prepayment2);

        when(dao.getPrepaymentsByReaderId(READER_ID)).thenReturn(expectedList);

        List<Prepayment> resultList = periodicalService.getPrepaymentsByReaderId(READER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void getPeriodicalsForSubscribingTest() {
        List<Periodical> expectedList = new ArrayList<>();
        List<Long> subscribedPeriodicals = new ArrayList<>();
        subscribedPeriodicals.add(periodical3.getId());
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(dao.getPeriodicalIdByReaderId(READER_ID)).thenReturn(subscribedPeriodicals);
        when(dao.getPeriodicalsForSubscribing(subscribedPeriodicals)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getPeriodicalsForSubscribing(READER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void getPeriodicalsForSubscribingByTopicByReaderIdTest() {
        List<Periodical> expectedList = new ArrayList<>();
        List<Long> subscribedPeriodicals = new ArrayList<>();
        subscribedPeriodicals.add(periodical3.getId());
        expectedList.add(periodical1);
        expectedList.add(periodical2);
        expectedList = expectedList.stream().filter(periodical -> periodical.getTopic().equals(TOPIC)).collect(Collectors.toList());

        when(dao.getPeriodicalIdByReaderId(READER_ID)).thenReturn(subscribedPeriodicals);
        when(dao.getPeriodicalsForSubscribing(subscribedPeriodicals)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.getPeriodicalsForSubscribingByTopicByReaderId(TOPIC, READER_ID);
        assertEquals(expectedList, resultList);
    }

    @Test
    public void findPeriodicalsForSubscribingByNameByReaderIdTest() {
        List<Periodical> expectedList = new ArrayList<>();
        List<Long> subscribedPeriodicals = new ArrayList<>();
        subscribedPeriodicals.add(periodical3.getId());
        expectedList.add(periodical1);
        expectedList.add(periodical2);

        when(dao.getPeriodicalIdByReaderId(READER_ID)).thenReturn(subscribedPeriodicals);
        when(dao.findPeriodicalsForSubscribingByName(subscribedPeriodicals, NAME)).thenReturn(expectedList);

        List<Periodical> resultList = periodicalService.findPeriodicalsForSubscribingByNameByReaderId(NAME, READER_ID);
        assertEquals(expectedList, resultList);
    }
}
