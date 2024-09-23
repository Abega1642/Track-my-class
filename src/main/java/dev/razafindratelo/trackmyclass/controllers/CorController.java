package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.dto.CorDTO;
import dev.razafindratelo.trackmyclass.entity.cor.Cor;
import dev.razafindratelo.trackmyclass.services.corServices.CorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CorController {
    private final CorService corService;

    @GetMapping("/CORs")
    public ResponseEntity<List<Cor>> getAllCors() {
        return ResponseEntity.ok(corService.findAll());
    }

    @GetMapping("/COR/student/{std}")
    public ResponseEntity<List<Cor>> getAllStudentsCors(@PathVariable("std") String std) {
        return ResponseEntity.ok(corService.findByStudentRef(std));
    }

    @PostMapping("/COR/add")
    public ResponseEntity<List<Cor>> addCor(@RequestBody List<CorDTO> cors) {
        return new ResponseEntity<>(
                corService.addCors(cors),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/COR/update")
    public ResponseEntity<List<Cor>> updateCor() {
        corService.updateCorList();
        return getAllCors();
    }

    @DeleteMapping("/COR/delete/{corRef}")
    public ResponseEntity<Cor> deleteCorByCorRef(@PathVariable("corRef") String corRef) {
        return ResponseEntity.ok(corService.deleteCor(corRef));
    }
}
