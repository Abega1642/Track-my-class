package dev.razafindratelo.trackmyclass.services.corServices;

import dev.razafindratelo.trackmyclass.dto.CorDTO;
import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CorService {
    List<Cor> findAll();

    String corRefGenerator();

    Cor findCorByItsRef(String corRef);

    List<Cor> findByStudentRef(String studentRef);

    Cor addCor(Cor cor);

    List<Cor> addCors(List<CorDTO> cors);

    Cor deleteCor(String corRef);

    List<Cor> updateCorList();
}
