package me.mapokapo.features.teams;

import java.util.ArrayList;
import java.util.Collections;
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
		return teams.stream().filter(team -> team.getId() == teamId).findFirst();
	}

	/**
	 * Gets all teams in the repository.
	 * 
	 * @return A list of all teams.
	 */
	public List<Team> getAllTeams() {
		return Collections.unmodifiableList(teams);
	}

	/**
	 * Adds a team to the repository.
	 * 
	 * @param team The team to add.
	 * @return The added team.
	 * @throws IllegalArgumentException if the team already exists in the
	 *                                  repository.
	 */
	public Team addTeam(Team team) {
		if (teams.stream().anyMatch(t -> t.getId() == team.getId())) {
			throw new IllegalArgumentException("Team with ID " + team.getId() + " already exists.");
		}

		teams.add(team);

		return team;
	}

	/**
	 * Removes a team from the repository.
	 * 
	 * @param teamId The ID of the team to remove.
	 * @throws IllegalArgumentException if the team does not exist in the
	 *                                  repository.
	 */
	public void removeTeam(int teamId) {
		Optional<Team> team = getTeamById(teamId);

		if (team.isPresent()) {
			teams.remove(team.get());
		} else {
			throw new IllegalArgumentException("Team with ID " + teamId + " does not exist.");
		}
	}
}
