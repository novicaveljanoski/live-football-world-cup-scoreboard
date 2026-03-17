package com.worldcup;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalArgumentException("Home team name cannot be null or empty");
        }
        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Away team name cannot be null or empty");
        }

        matches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }

        Match match = null;
        for (Match m : matches) {
            if (m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam)) {
                match = m;
                break;
            }
        }
        if (match == null) throw new IllegalArgumentException("Match not found");

        match.updateScore(homeScore, awayScore);
    }

    public List<Match> getSummary() {
        return matches;
    }
}
