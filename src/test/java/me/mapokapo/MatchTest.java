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
    /**
     * This method creates a sample match with two teams.
     * 
     * The returned values:
     * <ul>
     * <li>Match ID: 0</li>
     * <li>Home Team ID: 0</li>
     * <li>Away Team ID: 1</li>
     * <li>Home Team Name: "Home Team"</li>
     * <li>Away Team Name: "Away Team"</li>
     * </ul>
     * 
     * @return A sample match object.
     */
    private Match createSampleMatch() {
        int matchId = 0;
        int homeTeamId = 0;
        int awayTeamId = 1;
        String homeTeamName = "Home Team";
        String awayTeamName = "Away Team";

        Team homeTeam = new Team(homeTeamId, homeTeamName);
        Team awayTeam = new Team(awayTeamId, awayTeamName);
        return new Match(matchId, homeTeam, awayTeam);
    }

    @Test
    void givenCorrectData_whenMatchConstructed_thenMatchCreated() {
        // Arrange & Act
        var match = createSampleMatch();

        // Assert
        assertTrue(match.getId() == 0);
        assertTrue(match.getHomeTeam().getName().equals("Home Team"));
        assertTrue(match.getAwayTeam().getName().equals("Away Team"));
    }

    @Test
    void givenSameTeamAsBothHomeAndAway_whenMatchConstructed_thenThrowError() {
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
    void givenNotStartedMatch_whenSettingScore_thenThrowError() {
        // Arrange & Act
        var match = createSampleMatch();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            match.setScore(1, 2);
        });
    }

    @Test
    void givenFinishedMatch_whenSettingScore_thenThrowError() {
        // Arrange & Act
        var match = createSampleMatch();
        match.start();
        match.finish();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            match.setScore(1, 2);
        });
    }

    @Test
    void givenNegativeScore_whenSettingScore_thenThrowError() {
        // Arrange & Act
        var match = createSampleMatch();
        match.start();

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            match.setScore(-1, 1);
        });
    }

    @Test
    void givenNotStartedMatch_whenStartingMatch_thenMatchStarted() {
        // Arrange & Act
        var match = createSampleMatch();

        // Assert
        match.start();
        assertTrue(match.isStarted());
    }

    @Test
    void givenStartedMatch_whenStartingMatch_thenThrowError() {
        // Arrange & Act
        var match = createSampleMatch();
        match.start();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            match.start();
        });
    }

    @Test
    void givenNotStartedMatch_whenFinishingMatch_thenThrowError() {
        // Arrange & Act
        var match = createSampleMatch();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            match.finish();
        });
    }

    @Test
    void givenStartedMatch_whenFinishingMatch_thenMatchFinished() {
        // Arrange & Act
        var match = createSampleMatch();
        match.start();
        match.finish();

        // Assert
        assertTrue(match.isFinished());
    }

    @Test
    void givenFinishedMatch_whenFinishingMatch_thenThrowError() {
        // Arrange & Act
        var match = createSampleMatch();
        match.start();
        match.finish();

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            match.finish();
        });
    }
}
