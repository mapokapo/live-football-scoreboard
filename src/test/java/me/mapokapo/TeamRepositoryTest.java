package me.mapokapo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.mapokapo.features.teams.Team;
import me.mapokapo.features.teams.TeamRepository;

/**
 * Tests for the {@link TeamRepository} class.
 */
public class TeamRepositoryTest {
    private TeamRepository teamRepository;

    @BeforeEach
    void init() {
        teamRepository = new TeamRepository();
    }

    @Test
    void givenExistingTeamId_whenGettingTeam_thenTeamReturned() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);

        // Assert
        Optional<Team> retrievedTeam = teamRepository.getTeamById(teamId);
        assertTrue(retrievedTeam.isPresent());
    }

    @Test
    void givenNonexistentTeamId_whenGettingTeam_thenNoTeamReturned() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);

        // Assert
        Optional<Team> retrievedTeam = teamRepository.getTeamById(1);
        assertTrue(retrievedTeam.isEmpty());
    }

    @Test
    void givenEmptyDataSource_whenGettingAllTeams_thenReturnEmptyList() {
        // Act
        var teams = teamRepository.getAllTeams();

        // Assert
        assertTrue(teams.isEmpty());
    }

    @Test
    void givenDataSourceWithOneTeam_whenGettingAllTeams_thenReturnListWithOneTeam() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);

        // Assert
        var teams = teamRepository.getAllTeams();
        assertTrue(teams.size() == 1);
    }

    @Test
    void givenModifyingReturnedUnmodifiableListOfTeams_whenGettingAllTeams_thenThrowError() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);

        // Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            teamRepository.getAllTeams().clear();
        });
    }

    @Test
    void givenEmptyDataSource_whenAddingNewTeam_thenAddNewTeam() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        Team addedTeam = teamRepository.addTeam(team);

        // Assert
        assertTrue(addedTeam.getId() == teamId);
        assertTrue(teamRepository.getAllTeams().size() == 1);
    }

    @Test
    void givenNonEmptyDataSource_whenAddingNewUniqueTeam_thenAddNewTeam() {
        // Arrange
        int team1Id = 0;
        int team2Id = 1;
        String team1Name = "Team 1";
        String team2Name = "Team 2";

        // Act
        Team team1 = new Team(team1Id, team1Name);
        teamRepository.addTeam(team1);
        Team team2 = new Team(team2Id, team2Name);
        Team addedTeam = teamRepository.addTeam(team2);

        // Assert
        assertTrue(addedTeam.getId() == team2Id);
        assertTrue(teamRepository.getAllTeams().size() == 2);
        assertTrue(teamRepository.getTeamById(team1Id).isPresent());
        assertTrue(teamRepository.getTeamById(team2Id).isPresent());
    }

    @Test
    void givenExistingTeam_whenAddingNewTeam_thenThrowError() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            teamRepository.addTeam(team);
        });
    }

    @Test
    void givenExistingTeamId_whenRemovingTeam_thenRemoveTeam() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);
        teamRepository.removeTeam(teamId);

        // Assert
        assertTrue(teamRepository.getAllTeams().isEmpty());
    }

    @Test
    void givenNonexistentTeamId_whenRemovingTeam_thenThrowError() {
        // Arrange
        int teamId = 0;
        String teamName = "Team";

        // Act
        Team team = new Team(teamId, teamName);
        teamRepository.addTeam(team);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            teamRepository.removeTeam(1);
        });
    }
}
