package me.mapokapo.features.scoreboard;

import java.util.List;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.matches.MatchRepository;

/**
 * This class manages multiple matches. It functions as a repository for
 * {@link Match} objects.
 * 
 * <p>
 * Supported operations:
 * </p>
 * 
 * <ul>
 * <li>Add a match</li>
 * <li>Get a match by ID</li>
 * <li>Get all matches</li>
 * <li>Finish a match by ID</li>
 * <li>Get a summary of all matches</li>
 * </ul>
 */
public class Scoreboard {
	private final MatchRepository matchRepository;

	public Scoreboard(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	/**
	 * Finishes a match by ID. This method is simply a proxy for the
	 * {@link MatchRepository#removeMatch(int)}
	 * method.
	 * 
	 * @param matchId The ID of the match to finish.
	 */
	public void finishMatch(int matchId) {
		// TODO
	}

	/**
	 * Returns a specially-sorted list of matches which serves as a summary of the
	 * current scoreboard.
	 * 
	 * @note The summary is sorted according to the following criteria:
	 *       - Matches are sorted by the number of goals scored in descending order
	 *       (i.e. total goals).
	 *       - If two matches have the same number of goals, the match which was
	 *       inserted last is listed first.
	 * 
	 * @return A list of matches sorted according to special criteria.
	 */
	public List<Match> getSummary() {
		// TODO
		return null;
	}
}