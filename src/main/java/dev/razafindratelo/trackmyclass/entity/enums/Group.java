package dev.razafindratelo.trackmyclass.entity.enums;

import lombok.Getter;

@Getter
public enum Group {
    J1(Level.L1),
    J2(Level.L1),
    H1(Level.L2),
    H2(Level.L2),
    G1(Level.L3),;

    final Level level;
    Group(Level level) {
        this.level = level;
    }
}
