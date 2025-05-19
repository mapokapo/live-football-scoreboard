package me.mapokapo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.teams.Team;

/**
 * Tests for the {@link Match} class.
 */
public class MatchTest {
    @Test
    void givenCorrectData_whenMatchConstructed_thenMatchCreated() {
        // Arrange
        int matchId = 0;
        int homeTeamId = 0;
        int awayTeamId = 1;
        String homeTeamName = "Home Team";
        String awayTeamName = "Away Team";

        // Act
        Team homeTeam = new Team(homeTeamId, homeTeamName);
        Team awayTeam = new Team(awayTeamId, awayTeamName);
        Match match = new Match(matchId, homeTeam, awayTeam);

        // Assert
        assertTrue(match.getId() == matchId);
        assertTrue(match.getHomeTeam().getName().equals(homeTeamName));
        assertTrue(match.getAwayTeam().getName().equals(awayTeamName));
    }

    @Test
    void givenSameTeamAsBothHomeAndAway_whenMatchConstructed_thenErrorThrown() {
        // Arrange
        int matchId = 0;
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Match(matchId, team, team);
        });
    }

    @Test
    void givenMatchNotStarted_whenScoreSet_thenErrorThrown() {
        // Arrange
        int matchId = 0;
        int homeTeamId = 0;
        int awayTeamId = 1;
        String homeTeamName = "Home Team";
        String awayTeamName = "Away Team";

        // Act
        Team homeTeam = new Team(homeTeamId, homeTeamName);
        Team awayTeam = new Team(awayTeamId, awayTeamName);
        Match match = new Match(matchId, homeTeam, awayTeam);

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            match.setScore(1, 2);
        });
    }

    @Test
    void givenNegativeScore_whenSetScoreCalled_thenErrorThrown() {
        // Arrange
        int matchId = 0;
        int homeTeamId = 0;
        int awayTeamId = 1;
        String homeTeamName = "Home Team";
        String awayTeamName = "Away Team";

        // Act
        Team homeTeam = new Team(homeTeamId, homeTeamName);
        Team awayTeam = new Team(awayTeamId, awayTeamName);
        Match match = new Match(matchId, homeTeam, awayTeam);
        match.start();

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            match.setScore(-1, 1);
        });
    }
}
