package me.mapokapo.features.matches;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class serves as a data repository for {@link Match} objects.
 * 
 * @note The data source of this repository is a temporary in-memory solution
 *       for now, consisting of a simple ArrayList.
 */
public class MatchRepository {
	/**
	 * Data source for matches.
	 * 
	 * @note This is a temporary in-memory solution for now, consisting of a simple
	 *       ArrayList.
	 */
	private final List<Match> matches = new ArrayList<>();

	/**
	 * Gets a match by its ID.
	 * 
	 * @param matchId The ID of the match to get.
	 * @return An Optional containing the match if found, otherwise an empty
	 *         Optional.
	 */
	public Optional<Match> getMatchById(int matchId) {
		// TODO
		return null;
	}

	/**
	 * Gets all matches in the repository.
	 * 
	 * @return A list of all matches.
	 */
	public List<Match> getAllMatches() {
		// TODO
		return null;
	}

	/**
	 * Adds a match to the repository.
	 * 
	 * @param match The match to add.
	 * @return The added match.
	 * @throws IllegalArgumentException if the match already exists in the
	 *                                  repository.
	 */
	public Match addMatch(Match match) {
		// TODO
		return null;
	}

	/**
	 * Removes a match from the repository.
	 * 
	 * @param matchId The ID of the match to remove.
	 */
	public void removeMatch(int matchId) {
		// TODO
	}
}
