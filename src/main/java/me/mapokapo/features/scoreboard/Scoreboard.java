package me.mapokapo.features.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	 * Finishes a match by ID. This subsequently removes the match from the match
	 * repository.
	 * 
	 * @param matchId The ID of the match to finish.
	 * @throws IllegalArgumentException if the match is already finished or does not
	 *                                  exist.
	 * @throws IllegalStateException    if the match has not been started yet.
	 */
	public void finishMatch(int matchId) {
		Optional<Match> maybeMatch = matchRepository.getMatchById(matchId);

		if (maybeMatch.isEmpty()) {
			throw new IllegalArgumentException("Match already finished or does not exist");
		}

		Match match = maybeMatch.get();

		if (!match.isStarted()) {
			throw new IllegalStateException("Match has not started yet");
		}

		matchRepository.removeMatch(matchId);
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
		// The returned list of matches is an unmodifiable view of the original,
		// so we need to create a clone.
		List<Match> matches = new ArrayList<>(matchRepository.getAllMatches());

		// This method sorts in-place (does not create a new list), so the `matches`
		// list needs to be modifiable, which is why we cloned it above.
		matches.sort((match1, match2) -> {
			int totalGoals1 = match1.getHomeScore() + match1.getAwayScore();
			int totalGoals2 = match2.getHomeScore() + match2.getAwayScore();

			if (totalGoals1 != totalGoals2) {
				return Integer.compare(totalGoals2, totalGoals1);
			}

			// A simple way to sort by insertion order is to use the match ID, which
			// increments with each new match.
			return Integer.compare(match2.getId(), match1.getId());
		});

		return matches;
	}
}