package me.mapokapo.features.matches;

import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import me.mapokapo.features.teams.Team;
import lombok.AccessLevel;

/**
 * This class represents a match between two teams.
 * 
 * The match is not started by default. You must call the {@link #start()}
 * method to start the match.
 * 
 * @author Leo Petrović
 * @since 1.0
 */
@Data
public class Match {
	/**
	 * The unique identifier for the match.
	 */
	private final int id;

	/**
	 * The home team of the match.
	 */
	@NonNull
	private final Team homeTeam;

	/**
	 * The away team of the match.
	 */
	@NonNull
	private final Team awayTeam;

	/**
	 * The score of the home team.
	 * 
	 * You should not set this field directly. Use the {@link #setScore(int,
	 * int)} method to set the score.
	 */
	@Setter(AccessLevel.NONE)
	private int homeScore = 0;

	/**
	 * The score of the away team.
	 * 
	 * You should not set this field directly. Use the {@link #setScore(int,
	 * int)} method to set the score.
	 */
	@Setter(AccessLevel.NONE)
	private int awayScore = 0;

	/**
	 * Whether the match has started or not.
	 * 
	 * You should not set this field directly. Use the {@link #start()} method
	 * to start the match.
	 */
	@Setter(AccessLevel.NONE)
	private boolean isStarted = false;

	/**
	 * Whether the match has finished or not.
	 * 
	 * You should not set this field directly. Use the {@link #finish()}
	 * method to finish the match.
	 */
	@Setter(AccessLevel.NONE)
	private boolean isFinished = false;

	/**
	 * Constructs a new match with the given ID, home team, and away team.
	 * 
	 * @param id       The unique identifier for this match.
	 * @param homeTeam The home team of the match.
	 * @param awayTeam The away team of the match.
	 * 
	 * @throws IllegalArgumentException If the home team and away team are the same
	 *                                  or have the same ID.
	 */
	public Match(int id, Team homeTeam, Team awayTeam) {
		this.id = id;

		if (homeTeam.equals(awayTeam) || homeTeam.getId() == awayTeam.getId()) {
			throw new IllegalArgumentException("Home team and away team cannot be the same.");
		}

		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}

	/**
	 * Sets the absolute score for the match.
	 * 
	 * @param homeScore The score of the home team.
	 * @param awayScore The score of the away team.
	 * 
	 * @throws IllegalStateException    If the match has not started yet.
	 * @throws IllegalStateException    If the match has already finished.
	 * @throws IllegalArgumentException If the score is negative.
	 */
	public void setScore(int homeScore, int awayScore) {
		if (!isStarted) {
			throw new IllegalStateException("Match has not started yet.");
		}

		if (isFinished) {
			throw new IllegalStateException("Match has already finished.");
		}

		if (homeScore < 0 || awayScore < 0) {
			throw new IllegalArgumentException("Score cannot be negative.");
		}

		this.homeScore = homeScore;
		this.awayScore = awayScore;
	}

	/**
	 * Starts the match.
	 * 
	 * @throws IllegalStateException If the match has already finished.
	 * @throws IllegalStateException If the match has already started.
	 */
	public void start() {
		if (isFinished) {
			throw new IllegalStateException("Match has already finished.");
		}

		if (isStarted) {
			throw new IllegalStateException("Match has already started.");
		}

		isStarted = true;
	}

	/**
	 * Finishes the match.
	 * 
	 * @throws IllegalStateException If the match has not started yet.
	 * @throws IllegalStateException If the match has already finished.
	 */
	public void finish() {
		if (!isStarted) {
			throw new IllegalStateException("Match has not started yet.");
		}

		if (isFinished) {
			throw new IllegalStateException("Match has already finished.");
		}

		isFinished = true;
	}
}
