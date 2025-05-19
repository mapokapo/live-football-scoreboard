package me.mapokapo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import me.mapokapo.features.teams.Team;

/**
 * Tests for the {@link Match} class.
 */
public class TeamTest {
	@Test
	void givenCorrectData_whenTeamConstructed_thenTeamCreated() {
		// Arrange
		int teamId = 0;
		String teamName = "Team";

		// Act
		Team team = new Team(teamId, teamName);

		// Assert
		assertTrue(team.getId() == teamId);
		assertTrue(team.getName().equals(teamName));
	}
}
