package me.mapokapo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.matches.MatchRepository;
import me.mapokapo.features.teams.Team;

/**
 * Tests for the {@link MatchRepository} class.
 */
public class MatchRepositoryTest {
    private MatchRepository matchRepository;

    @BeforeEach
    void init() {
        matchRepository = new MatchRepository();
    }

    @Test
    void givenExistingMatchId_whenGettingMatch_thenMatchReturned() {
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
        matchRepository.addMatch(match);

        // Assert
        Optional<Match> retrievedMatch = matchRepository.getMatchById(matchId);
        assertTrue(retrievedMatch.isPresent());
    }

    @Test
    void givenNonexistentMatchId_whenGettingMatch_thenNoMatchReturned() {
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
        matchRepository.addMatch(match);

        // Assert
        Optional<Match> retrievedMatch = matchRepository.getMatchById(1);
        assertTrue(retrievedMatch.isEmpty());
    }

    @Test
    void givenEmptyDataSource_whenGettingAllMatches_thenReturnEmptyList() {
        // Act
        var matches = matchRepository.getAllMatches();

        // Assert
        assertTrue(matches.isEmpty());
    }

    @Test
    void givenDataSourceWithOneMatch_whenGettingAllMatches_thenReturnListWithOneMatch() {
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
        matchRepository.addMatch(match);

        // Assert
        var matches = matchRepository.getAllMatches();
        assertTrue(matches.size() == 1);
    }

    @Test
    void givenModifyingReturnedUnmodifiableListOfMatches_whenGettingAllMatches_thenThrowError() {
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
        matchRepository.addMatch(match);

        // Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            matchRepository.getAllMatches().clear();
        });
    }

    @Test
    void givenEmptyDataSource_whenAddingNewMatch_thenAddNewMatch() {
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
        Match addedMatch = matchRepository.addMatch(match);

        // Assert
        assertTrue(addedMatch.getId() == matchId);
        assertTrue(matchRepository.getAllMatches().size() == 1);
    }

    @Test
    void givenNonEmptyDataSource_whenAddingNewUniqueMatch_thenAddNewMatch() {
        // Arrange
        int match1Id = 0;
        int match2Id = 1;
        int homeTeam1Id = 0;
        int awayTeam1Id = 1;
        int homeTeam2Id = 2;
        int awayTeam2Id = 3;
        String homeTeam1Name = "Home Team 1";
        String awayTeam1Name = "Away Team 2";
        String homeTeam2Name = "Home Team 2";
        String awayTeam2Name = "Away Team 2";

        // Act
        Team homeTeam1 = new Team(homeTeam1Id, homeTeam1Name);
        Team awayTeam1 = new Team(awayTeam1Id, awayTeam1Name);
        Match match1 = new Match(match1Id, homeTeam1, awayTeam1);
        matchRepository.addMatch(match1);
        Team homeTeam2 = new Team(homeTeam2Id, homeTeam2Name);
        Team awayTeam2 = new Team(awayTeam2Id, awayTeam2Name);
        Match match2 = new Match(match2Id, homeTeam2, awayTeam2);
        Match addedMatch = matchRepository.addMatch(match2);

        // Assert
        assertTrue(addedMatch.getId() == match2Id);
        assertTrue(matchRepository.getAllMatches().size() == 2);
        assertTrue(matchRepository.getMatchById(match1Id).isPresent());
        assertTrue(matchRepository.getMatchById(match2Id).isPresent());
    }

    @Test
    void givenExistingMatch_whenAddingNewMatch_thenThrowError() {
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
        matchRepository.addMatch(match);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            matchRepository.addMatch(match);
        });
    }

    @Test
    void givenExistingMatchId_whenRemovingMatch_thenRemoveMatch() {
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
        matchRepository.addMatch(match);
        matchRepository.removeMatch(matchId);

        // Assert
        assertTrue(matchRepository.getAllMatches().isEmpty());
    }

    @Test
    void givenNonexistentMatchId_whenRemovingMatch_thenThrowError() {
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
        matchRepository.addMatch(match);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            matchRepository.removeMatch(1);
        });
    }
}
