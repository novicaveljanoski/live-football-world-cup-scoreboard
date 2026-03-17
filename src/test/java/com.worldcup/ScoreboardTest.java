package com.worldcup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
