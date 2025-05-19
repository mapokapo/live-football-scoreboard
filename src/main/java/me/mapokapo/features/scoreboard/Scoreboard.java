package me.mapokapo.features.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.matches.MatchRepository;
import me.mapokapo.features.teams.Team;
import me.mapokapo.features.teams.TeamRepository;

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
 * <li>Start and finish a match</li>
 * <li>Update the score of a live match</li>
 * <li>Finish a match currently in progress, which hides it from the
 * scoreboard summary</li>
 * <li>Get a summary of all matches in progress in a special order</li>
 * </ul>
 */
public class Scoreboard {
	private final MatchRepository matchRepository;
	private final TeamRepository teamRepository;

	public Scoreboard(MatchRepository matchRepository, TeamRepository teamRepository) {
		this.matchRepository = matchRepository;
		this.teamRepository = teamRepository;
	}

	/**
	 * Adds a match to the scoreboard.
	 * 
	 * @note The default state of the newly created match is:
	 *       <ul>
	 *       <li>Not started</li>
	 *       <li>Not finished</li>
	 *       <li>Scores are set to 0</li>
	 *       </ul>
	 * 
	 * @param homeTeam The home team.
	 * @param awayTeam The away team.
	 * @return The match object created.
	 */
	public Match addMatch(Team homeTeam, Team awayTeam) {
		Match match = new Match(matchRepository.getNextIndex(), homeTeam, awayTeam);

		matchRepository.addMatch(match);

		return match;
	}

	/**
	 * A shorthand method for adding a match to the scoreboard. This method
	 * automatically creates the teams for the match and adds them to the
	 * {@link TeamRepository}, using only the names of the teams.
	 * 
	 * @param homeTeamName The name of the home team.
	 * @param awayTeamName The name of the away team.
	 * @return The match object created.
	 */
	public Match addMatch(String homeTeamName, String awayTeamName) {
		Team homeTeam = teamRepository.addTeam(new Team(teamRepository.getNextIndex(), homeTeamName));
		Team awayTeam = teamRepository.addTeam(new Team(teamRepository.getNextIndex(), awayTeamName));
		Match match = new Match(matchRepository.getNextIndex(), homeTeam, awayTeam);

		matchRepository.addMatch(match);

		return match;
	}

	/**
	 * Starts a match by ID. This allows the user to update the score of the match.
	 * 
	 * @param matchId The ID of the match to start.
	 * @throws IllegalArgumentException if the match does not exist.
	 * @throws IllegalStateException    if the match is already finished.
	 * @throws IllegalStateException    if the match is already started.
	 */
	public void startMatch(int matchId) {
		Optional<Match> maybeMatch = matchRepository.getMatchById(matchId);

		if (maybeMatch.isEmpty()) {
			throw new IllegalArgumentException("Match does not exist");
		}

		Match match = maybeMatch.get();
		match.start();
	}

	/**
	 * Finishes a match by ID. This hides the match from the scoreboard summary but
	 * does not delete it.
	 * 
	 * @param matchId The ID of the match to finish.
	 * @throws IllegalArgumentException if the match does not exist.
	 * @throws IllegalStateException    if the match is already finished.
	 * @throws IllegalStateException    if the match has not been started yet.
	 */
	public void finishMatch(int matchId) {
		Optional<Match> maybeMatch = matchRepository.getMatchById(matchId);

		if (maybeMatch.isEmpty()) {
			throw new IllegalArgumentException("Match does not exist");
		}

		var match = maybeMatch.get();
		match.finish();
	}

	/**
	 * Updates the score of a match by ID. This method can only be called if the
	 * match is currently in progress.
	 * 
	 * @param matchId   The ID of the match to update.
	 * @param homeScore The new home score.
	 * @param awayScore The new away score.
	 * @throws IllegalArgumentException if the match does not exist.
	 * @throws IllegalStateException    If the match has not started yet.
	 * @throws IllegalStateException    If the match has already finished.
	 * @throws IllegalArgumentException If the score is negative.
	 */
	public void updateScore(int matchId, int homeScore, int awayScore) {
		Optional<Match> maybeMatch = matchRepository.getMatchById(matchId);

		if (maybeMatch.isEmpty()) {
			throw new IllegalArgumentException("Match does not exist");
		}

		Match match = maybeMatch.get();
		match.setScore(homeScore, awayScore);
	}

	/**
	 * Returns a specially-sorted list of currently-running matches which serves as
	 * a summary of the current scoreboard.
	 * 
	 * @note The summary is sorted according to the following criteria:
	 *       <ul>
	 *       <li>Matches are sorted by the number of goals scored in descending
	 *       order (i.e. total goals).</li>
	 *       <li>If two matches have the same number of goals, the match which was
	 *       inserted last is listed first.</li>
	 *       </ul>
	 * 
	 * @return A list of matches sorted according to special criteria.
	 */
	public List<Match> getSummary() {
		// Get all matches which are currently in progress (started but not finished).
		// The returned list of matches is an unmodifiable view of the original,
		// so we need to create a clone.
		List<Match> matches = new ArrayList<>(matchRepository.getAllMatches().stream()
				.filter(match -> match.isStarted() && !match.isFinished()).toList());

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