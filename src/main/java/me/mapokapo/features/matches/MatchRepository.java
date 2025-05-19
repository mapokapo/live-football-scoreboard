package me.mapokapo.features.matches;

import java.util.ArrayList;
import java.util.Collections;
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
	 * Gets the next available index for a new match. This is simply the size of
	 * the current list of matches.
	 * 
	 * @return The next available index to use for creating a new match.
	 */
	public int getNextIndex() {
		return matches.size();
	}

	/**
	 * Gets a match by its ID.
	 * 
	 * @param matchId The ID of the match to get.
	 * @return An Optional containing the match if found, otherwise an empty
	 *         Optional.
	 */
	public Optional<Match> getMatchById(int matchId) {
		return matches.stream().filter(match -> match.getId() == matchId).findFirst();
	}

	/**
	 * Gets all matches in the repository.
	 * 
	 * @return An unmodifiable list of all matches in the repository.
	 */
	public List<Match> getAllMatches() {
		return Collections.unmodifiableList(matches);
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
		if (matches.stream().anyMatch(m -> m.getId() == match.getId())) {
			throw new IllegalArgumentException("Match with ID " + match.getId() + " already exists.");
		}

		matches.add(match);

		return match;
	}

	/**
	 * Removes a match from the repository.
	 * 
	 * @param matchId The ID of the match to remove.
	 * @throws IllegalArgumentException if the match does not exist in the
	 *                                  repository.
	 */
	public void removeMatch(int matchId) {
		Optional<Match> match = getMatchById(matchId);

		if (match.isPresent()) {
			matches.remove(match.get());
		} else {
			throw new IllegalArgumentException("Match with ID " + matchId + " does not exist.");
		}
	}
}
