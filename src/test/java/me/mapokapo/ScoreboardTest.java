package me.mapokapo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.matches.MatchRepository;
import me.mapokapo.features.scoreboard.Scoreboard;
import me.mapokapo.features.teams.Team;
import me.mapokapo.features.teams.TeamRepository;

/**
 * Tests for the {@link Scoreboard} class.
 */
public class ScoreboardTest {
	private TeamRepository teamRepository;
	private MatchRepository matchRepository;
	private Scoreboard scoreboard;

	@BeforeEach
	void init() {
		teamRepository = new TeamRepository();
		matchRepository = new MatchRepository();
		scoreboard = new Scoreboard(matchRepository, teamRepository);
	}

	@Test
	void givenCorrectData_whenAddingMatch_thenCreateMatch() {
		// Arrange
		var team1 = new Team(0, "Team A");
		var team2 = new Team(1, "Team B");

		// Act
		var match = scoreboard.addMatch(team1, team2);

		// Assert
		assertTrue(matchRepository.getAllMatches().size() == 1);
		assertTrue(matchRepository.getMatchById(match.getId()).isPresent());
		assertTrue(match.getId() == 0);
		assertTrue(match.getHomeTeam().equals(team1));
		assertTrue(match.getAwayTeam().equals(team2));
		assertTrue(match.getHomeScore() == 0);
		assertTrue(match.getAwayScore() == 0);
	}

	@Test
	void givenSameTeamAsBothHomeAndAway_whenAddingMatch_thenThrowError() {
		// Arrange
		var team1 = new Team(0, "Team A");

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			scoreboard.addMatch(team1, team1);
		});
	}

	@Test
	void givenCorrectData_whenAddingShorthandMatch_thenCreateMatch() {
		// Act
		var match = scoreboard.addMatch("Team A", "Team B");

		// Assert
		assertTrue(matchRepository.getAllMatches().size() == 1);
		assertTrue(matchRepository.getMatchById(match.getId()).isPresent());
		assertTrue(match.getId() == 0);
		assertTrue(match.getHomeTeam().getName().equals("Team A"));
		assertTrue(match.getAwayTeam().getName().equals("Team B"));
		assertTrue(match.getHomeScore() == 0);
		assertTrue(match.getAwayScore() == 0);
	}

	@Test
	void givenNotStartedMatch_whenStartingMatch_thenStartMatch() {
		// Arrange
		var team1 = new Team(0, "Team A");
		var team2 = new Team(1, "Team B");
		var match = scoreboard.addMatch(team1, team2);

		// Act
		scoreboard.startMatch(match.getId());

		// Assert
		assertTrue(match.isStarted());
	}

	@Test
	void givenAlreadyStartedMatch_whenStartingMatch_thenThrowError() {
		// Arrange
		var team1 = new Team(0, "Team A");
		var team2 = new Team(1, "Team B");
		var match = scoreboard.addMatch(team1, team2);
		match.start();

		// Act & Assert
		assertThrows(IllegalStateException.class, () -> {
			scoreboard.startMatch(match.getId());
		});
	}

	@Test
	void givenNonexistentMatch_whenStartingMatch_thenThrowError() {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			scoreboard.startMatch(0);
		});
	}

	@Test
	void givenStartedMatch_whenUpdatingScore_thenUpdateScore() {
		// Arrange
		var team1 = new Team(0, "Team A");
		var team2 = new Team(1, "Team B");
		var match = scoreboard.addMatch(team1, team2);
		match.start();

		// Act
		scoreboard.updateScore(match.getId(), 1, 0);

		// Assert
		assertTrue(match.getHomeScore() == 1);
		assertTrue(match.getAwayScore() == 0);
	}

	@Test
	void givenNotStartedMatch_whenUpdatingScore_thenThrowError() {
		// Arrange
		var team1 = new Team(0, "Team A");
		var team2 = new Team(1, "Team B");
		var match = scoreboard.addMatch(team1, team2);

		// Act & Assert
		assertThrows(IllegalStateException.class, () -> {
			scoreboard.updateScore(match.getId(), 1, 0);
		});
	}

	@Test
	void givenFinishedMatch_whenUpdatingScore_thenThrowError() {
		// Arrange
		var team1 = new Team(0, "Team A");
		var team2 = new Team(1, "Team B");
		var match = scoreboard.addMatch(team1, team2);
		match.start();
		scoreboard.finishMatch(match.getId());

		// Act & Assert
		assertThrows(IllegalStateException.class, () -> {
			scoreboard.updateScore(match.getId(), 1, 0);
		});
	}

	@Test
	void givenEmptyDataSource_whenGettingSummary_thenReturnEmptyList() {
		// Act
		var summary = scoreboard.getSummary();

		// Assert
		assertTrue(summary.isEmpty());
	}

	@Test
	void givenNonEmptyDataSource_whenGettingSummary_thenReturnProperlySortedList() {
		// Arrange
		var team1 = teamRepository.addTeam(new Team(0, "Team A"));
		var team2 = teamRepository.addTeam(new Team(1, "Team B"));
		var team3 = teamRepository.addTeam(new Team(2, "Team C"));
		var team4 = teamRepository.addTeam(new Team(3, "Team D"));

		var match12 = matchRepository.addMatch(new Match(0, team1, team2));
		var match34 = matchRepository.addMatch(new Match(1, team3, team4));
		var match13 = matchRepository.addMatch(new Match(2, team1, team3));
		var match24 = matchRepository.addMatch(new Match(3, team2, team4));

		// Act
		match12.start();
		match34.start();
		match13.start();
		match24.start();

		match12.setScore(1, 0);
		match34.setScore(2, 1);
		match13.setScore(3, 0);
		match24.setScore(1, 4);

		// Current state:
		// 1. Match12: A 1 - 0 B
		// 2. Match34: C 2 - 1 D
		// 3. Match13: A 3 - 0 C
		// 4. Match24: B 1 - 4 D

		// Expected sorted list (sorted by total points descending, and if two matches
		// have the same points, then the last inserted match goes first):
		// 1. Match24: B 1 - 4 D
		// 2. Match13: A 3 - 0 C
		// 3. Match34: C 2 - 1 D
		// 4. Match12: A 1 - 0 B

		// Assert
		var summary = scoreboard.getSummary();
		var item1 = summary.get(0);
		var item2 = summary.get(1);
		var item3 = summary.get(2);
		var item4 = summary.get(3);

		assertTrue(summary.size() == 4);
		assertAll(
				() -> assertTrue(item1.equals(match24)),
				() -> assertTrue(item2.equals(match13)),
				() -> assertTrue(item3.equals(match34)),
				() -> assertTrue(item4.equals(match12)));
		assertAll(
				() -> assertTrue(item1.getHomeTeam().equals(team2)),
				() -> assertTrue(item1.getAwayTeam().equals(team4)),
				() -> assertTrue(item1.getHomeScore() == 1),
				() -> assertTrue(item1.getAwayScore() == 4));
		assertAll(
				() -> assertTrue(item2.getHomeTeam().equals(team1)),
				() -> assertTrue(item2.getAwayTeam().equals(team3)),
				() -> assertTrue(item2.getHomeScore() == 3),
				() -> assertTrue(item2.getAwayScore() == 0));
		assertAll(
				() -> assertTrue(item3.getHomeTeam().equals(team3)),
				() -> assertTrue(item3.getAwayTeam().equals(team4)),
				() -> assertTrue(item3.getHomeScore() == 2),
				() -> assertTrue(item3.getAwayScore() == 1));
		assertAll(
				() -> assertTrue(item4.getHomeTeam().equals(team1)),
				() -> assertTrue(item4.getAwayTeam().equals(team2)),
				() -> assertTrue(item4.getHomeScore() == 1),
				() -> assertTrue(item4.getAwayScore() == 0));
	}

	@Test
	void givenNotStartedMatch_whenGettingSummary_thenHideMatchFromSummary() {
		// Arrange
		var team1 = teamRepository.addTeam(new Team(0, "Team A"));
		var team2 = teamRepository.addTeam(new Team(1, "Team B"));
		matchRepository.addMatch(new Match(0, team1, team2)); // Match not started

		// Act
		var summary = scoreboard.getSummary();

		// Assert
		assertTrue(summary.isEmpty());
	}

	@Test
	void givenFinishedMatch_whenGettingSummary_thenHideMatchFromSummary() {
		// Arrange
		var team1 = teamRepository.addTeam(new Team(0, "Team A"));
		var team2 = teamRepository.addTeam(new Team(1, "Team B"));
		var match = matchRepository.addMatch(new Match(0, team1, team2));

		// Act
		match.start();
		match.setScore(1, 0);
		scoreboard.finishMatch(match.getId());

		// Assert
		var summary = scoreboard.getSummary();
		assertTrue(summary.isEmpty());
	}

	@Test
	void givenAlreadyFinishedMatch_whenFinishingMatch_thenThrowError() {
		// Arrange
		var team1 = teamRepository.addTeam(new Team(0, "Team A"));
		var team2 = teamRepository.addTeam(new Team(1, "Team B"));
		var match = matchRepository.addMatch(new Match(0, team1, team2));

		// Act
		match.start();
		scoreboard.finishMatch(match.getId());

		// Assert
		assertThrows(IllegalStateException.class, () -> {
			scoreboard.finishMatch(match.getId());
		});
	}

	@Test
	void givenNonexistentMatch_whenFinishingMatch_thenThrowError() {
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			scoreboard.finishMatch(0);
		});
	}

	@Test
	void givenNotStartedMatch_whenFinishingMatch_thenThrowError() {
		// Arrange
		var team1 = teamRepository.addTeam(new Team(0, "Team A"));
		var team2 = teamRepository.addTeam(new Team(1, "Team B"));
		var match = matchRepository.addMatch(new Match(0, team1, team2));

		// Act & Assert
		assertThrows(IllegalStateException.class, () -> {
			scoreboard.finishMatch(match.getId());
		});
	}
}
