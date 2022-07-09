package periodicals.epam.com.project.logic.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import periodicals.epam.com.project.logic.dao.ReaderDAO;
import periodicals.epam.com.project.logic.entity.Reader;
import periodicals.epam.com.project.logic.entity.dto.ReaderCreateDTO;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {
    @Mock
    private ReaderDAO dao;

    @InjectMocks
    private ReaderService readerService;

    @Test
    public void createReader(){
        ReaderCreateDTO dto = new ReaderCreateDTO(1L,"login","password");
        Reader expectedReader = new Reader();
        expectedReader.setId(dto.getId());
        expectedReader.setLogin(dto.getLogin());

        when(dao.insertReader(dto)).thenReturn(dto);

        Reader resultReader = readerService.createReader(dto);
        assertEquals(expectedReader,resultReader);

//        verify(resultReader).setId(1L);
//        verify(resultReader).setLogin("login");
    }
}
