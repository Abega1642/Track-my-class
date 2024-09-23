package dev.razafindratelo.trackmyclass.services.corServices;

import dev.razafindratelo.trackmyclass.dao.CorDAO;
import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CorServiceImpl implements CorService {
    private CorDAO corDAO;

    @Override
    public List<Cor> findAll() {
        return corDAO.getAllCors();
    }

    @Override
    public Cor findById(String corRef) {
        return findAll()
                .stream()
                .filter(cor -> cor.getCorRef().equalsIgnoreCase(corRef))
                .toList()
                .getFirst();
    }

    @Override
    public List<Cor> findByStudentRef(String studentRef) {
        return findAll()
                .stream()
                .filter(cor -> cor.getStudent().getUserRef().equalsIgnoreCase(studentRef))
                .toList();
    }
}
