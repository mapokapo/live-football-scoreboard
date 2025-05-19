package me.mapokapo.features.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class serves as a data repository for {@link Team} objects.
 * 
 * @note The data source of this repository is a temporary in-memory solution
 *       for now, consisting of a simple ArrayList.
 */
public class TeamRepository {
	/**
	 * Data source for teams.
	 * 
	 * @note This is a temporary in-memory solution for now, consisting of a simple
	 *       ArrayList.
	 */
	private final List<Team> teams = new ArrayList<>();

	/**
	 * Gets a team by its ID.
	 * 
	 * @param teamId The ID of the team to get.
	 * @return An Optional containing the team if found, otherwise an empty
	 *         Optional.
	 */
	public Optional<Team> getTeamById(int teamId) {
		// TODO
		return null;
	}

	/**
	 * Gets all teams in the repository.
	 * 
	 * @return A list of all teams.
	 */
	public List<Team> getAllTeams() {
		// TODO
		return null;
	}

	/**
	 * Adds a team to the repository.
	 * 
	 * @param team The team to add.
	 * @return The added team.
	 */
	public Team addTeam(Team team) {
		// TODO
		return null;
	}

	/**
	 * Removes a team from the repository.
	 * 
	 * @param teamId The ID of the team to remove.
	 */
	public void removeTeam(int teamId) {
		// TODO
	}
}
