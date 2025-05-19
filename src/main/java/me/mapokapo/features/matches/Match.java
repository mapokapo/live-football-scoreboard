package me.mapokapo.features.matches;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.mapokapo.features.teams.Team;
import lombok.AccessLevel;

/**
 * This class represents a match between two teams.
 * 
 * @note The match is not started by default. You must call the {@link #start()}
 *       method to start the match.
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
	private final Team homeTeam;

	/**
	 * The away team of the match.
	 */
	private final Team awayTeam;

	/**
	 * The score of the home team.
	 * 
	 * @note You should not set this field directly. Use the {@link #setScore(int,
	 *       int)} method to set the score.
	 */
	@Setter(AccessLevel.NONE)
	private int homeScore = 0;

	/**
	 * The score of the away team.
	 * 
	 * @note You should not set this field directly. Use the {@link #setScore(int,
	 *       int)} method to set the score.
	 */
	@Setter(AccessLevel.NONE)
	private int awayScore = 0;

	/**
	 * Whether the match has started or not.
	 * 
	 * @note You should not set this field directly. Use the {@link #start()} method
	 *       to start the match.
	 */
	@Setter(AccessLevel.NONE)
	private boolean isStarted = false;

	/**
	 * Constructs a new match with the given ID, home team, and away team.
	 * 
	 * @note The teams must be different, otherwise an
	 *       {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param id       The unique identifier for this match.
	 * @param homeTeam The home team of the match.
	 * @param awayTeam The away team of the match.
	 */
	public Match(int id, Team homeTeam, Team awayTeam) {
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}

	/**
	 * Sets the absolute score for the match.
	 * 
	 * @param homeScore The score of the home team.
	 * @param awayScore The score of the away team.
	 */
	public void setScore(int homeScore, int awayScore) {
		// TODO
	}

	/**
	 * Starts the match.
	 * 
	 * @note You must call this method to start the match. If you attempt to set
	 *       team scores before starting the match,
	 *       a {@link IllegalStateException} will be thrown.
	 */
	public void start() {
		// TODO
	}
}
