package dev.razafindratelo.trackmyclass.dao;

import dev.razafindratelo.trackmyclass.dao.repository.DBConnection;
import dev.razafindratelo.trackmyclass.entity.matchers.LevelGroupMatcher;
import dev.razafindratelo.trackmyclass.entity.users.enums.Group;
import dev.razafindratelo.trackmyclass.entity.users.enums.Level;
import dev.razafindratelo.trackmyclass.exceptionHandler.InternalException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
@AllArgsConstructor
public class GroupAndLevelDAO {
    private DBConnection dbConnection;

    public Level upgradeLevelToNextLevel(Level level) {
        int n = Integer.parseInt(level.toString().substring(1,2));
        String letter = level.toString().substring(0,1);
        if(letter.equals("L") && n == 3) {
            level = Level.M1;
        } else if (letter.equals("M") && n == 2) {
            level = Level.D1;
        } else {
            n++;
            level = Level.valueOf(letter + n);
        }
        return level;
    }

    public List<Level> allLevels() {
        return Arrays.stream(Level.values())
                .toList()
                .reversed().
                stream()
                .filter(level -> !level.equals(Level.D1))
                .toList();
    }

    public List<Group> allGroups() {
        return Arrays.stream(Group.values()).toList();
    }

    public List<LevelGroupMatcher> getAllRelationOfLeveAndGroup() {
        try {
            PreparedStatement getAll = dbConnection
                    .getConnection()
                    .prepareStatement(
                            "SELECT level_year, \"group\" FROM group_level_matcher"
                    );
            getAll.execute();
            ResultSet resultSet = getAll.getResultSet();

            List<LevelGroupMatcher> levelGroupMatchers = new ArrayList<>();
            while(resultSet.next()) {
                LevelGroupMatcher matcher = new LevelGroupMatcher(
                        Level.valueOf(resultSet.getString("level_year")),
                        Group.valueOf(resultSet.getString("group"))
                );
                levelGroupMatchers.add(matcher);
            }
            return levelGroupMatchers;

        } catch (SQLException e) {
            throw new InternalException("Error while retrieving all relation of groups and level year := " + e.getMessage());
        }
    }

    public List<LevelGroupMatcher> getAllRelationOfGroupByLevel(Level level) {
        return getAllRelationOfLeveAndGroup().stream().filter(el -> el.getLevel().equals(level)).toList();
    }
    private List<LevelGroupMatcher> updateSpecificLevel(Level level) {
        try {
            PreparedStatement update = dbConnection
                    .getConnection()
                    .prepareStatement(
                            """
                                  UPDATE group_level_matcher SET
                                    level_year = ?
                                  WHERE level_year = ?
                                 """
                    );
            Level updatedLevelYear = upgradeLevelToNextLevel(level);
            update.setString(1, updatedLevelYear.toString());
            update.setString(2, level.toString());

            update.executeUpdate();

            return getAllRelationOfGroupByLevel(level);

        } catch(SQLException e) {
            throw new InternalException("Error while updating levels := " + e.getMessage());
        }
    }

    public List<LevelGroupMatcher> updateGroupLevels() {
        for(Level level : allLevels()) {
            updateSpecificLevel(level);
        }
        return getAllRelationOfLeveAndGroup();
    }

    public boolean doesLevelMatchGroup(Group group, Level level) {
        LevelGroupMatcher map = new LevelGroupMatcher(level, group);
        return getAllRelationOfLeveAndGroup().contains(map);
    }

}
