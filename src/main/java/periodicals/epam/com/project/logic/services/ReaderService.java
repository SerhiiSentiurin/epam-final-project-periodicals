package periodicals.epam.com.project.logic.services;

import lombok.RequiredArgsConstructor;
import periodicals.epam.com.project.logic.dao.ReaderDAO;
import periodicals.epam.com.project.logic.entity.Reader;
import periodicals.epam.com.project.logic.entity.dto.ReaderCreateDTO;

@RequiredArgsConstructor
public class ReaderService {
    private final ReaderDAO readerDAO;

    public Reader createReader(ReaderCreateDTO readerCreateDTO){
        Reader reader = new Reader();
        ReaderCreateDTO dto = readerDAO.insertReader(readerCreateDTO);
        reader.setId(dto.getId());
        reader.setLogin(dto.getLogin());
        return reader;
    }
}
