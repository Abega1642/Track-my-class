package dev.razafindratelo.trackmyclass.services.corServices;

import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CorService {
    List<Cor> findAll();

    Cor findById(String corRef);

    List<Cor> findByStudentRef(String studentRef);
}
