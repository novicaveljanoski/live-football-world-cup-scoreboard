package com.worldcup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void shouldStartMatchWithInitialScoreZeroZero() {
        scoreboard.startMatch("Mexico", "Canada");

        Match match = scoreboard.getSummary().getFirst();

        assertEquals("Mexico", match.getHomeTeam());
        assertEquals("Canada", match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch(null, "Canada"));
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamIsNull() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Mexico", null));
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("", "Canada"));
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.startMatch("Mexico", ""));
    }

    @Test
    void shouldUpdateScore() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 1, 0);

        Match match = scoreboard.getSummary().getFirst();

        assertEquals(1, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentMatch() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Mexico", "Canada", 1, 0));
    }

    @Test
    void shouldThrowExceptionWhenHomeScoreIsNegative() {
        scoreboard.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Mexico", "Canada", -1, 0));
    }

    @Test
    void shouldThrowExceptionWhenAwayScoreIsNegative() {
        scoreboard.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore("Mexico", "Canada", 0, -1));
    }

    @Test
    void shouldFinishMatch() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.finishMatch("Mexico", "Canada");

        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    void shouldThrowExceptionWhenFinishingNonExistentMatch() {
        assertThrows(IllegalArgumentException.class, () -> scoreboard.finishMatch("Mexico", "Canada"));
    }

    @Test
    void shouldReturnEmptySummaryWhenNoMatchesInProgress() {
        assertEquals(0, scoreboard.getSummary().size());
    }

    @Test
    void shouldReturnMatchesOrderedByTotalScoreDescending() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        assertEquals("Spain", scoreboard.getSummary().get(0).getHomeTeam());
        assertEquals("Mexico", scoreboard.getSummary().get(1).getHomeTeam());
    }

    @Test
    void shouldReturnMatchesWithSameTotalScoreOrderedByMostRecentlyStarted() {
        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        assertEquals("Argentina", scoreboard.getSummary().get(0).getHomeTeam());
        assertEquals("Germany", scoreboard.getSummary().get(1).getHomeTeam());
    }

    @Test
    void shouldReturnCorrectSummaryForAllMatchesFromSpec() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Germany", summary.get(4).getHomeTeam());
    }
}
