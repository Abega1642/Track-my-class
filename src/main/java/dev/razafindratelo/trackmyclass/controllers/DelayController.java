package dev.razafindratelo.trackmyclass.controllers;

import dev.razafindratelo.trackmyclass.dao.DelayDAO;
import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.matchers.DelayMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DelayController {

    @GetMapping("/test")
    public List<DelayMatcher> test() {
        var delayDAO = new DelayDAO(new DBConnection());
        return delayDAO.getAllDelays();
    }
}
