package me.mapokapo.features.teams;

import lombok.Data;

/**
 * This class represents a football team, not related to a currently-running
 * match..
 * 
 * @author Leo Petrović
 * @since 1.0
 */
@Data
public class Team {
	/**
	 * The unique identifier for the team.
	 */
	private final int id;

	/**
	 * The name of the team.
	 */
	private final String name;
}
