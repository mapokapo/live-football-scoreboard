package me.mapokapo;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.matches.MatchRepository;
import me.mapokapo.features.scoreboard.Scoreboard;
import me.mapokapo.features.teams.Team;
import me.mapokapo.features.teams.TeamRepository;

public class App {
    public static void main(String[] args) {
        TeamRepository teamRepository = new TeamRepository();
        MatchRepository matchRepository = new MatchRepository();

        var team1 = teamRepository.addTeam(new Team(0, "Team A"));
        var team2 = teamRepository.addTeam(new Team(1, "Team B"));

        var match1 = matchRepository.addMatch(new Match(0, team1, team2));

        Scoreboard scoreboard = new Scoreboard(matchRepository);

        // Match must be started before setting scores
        match1.start();
        // First goal
        match1.setScore(0, 1);
        // Second goal
        match1.setScore(1, 1);
        // Third goal
        match1.setScore(1, 2);

        // Finish the match
        scoreboard.finishMatch(match1.getId());
    }
}
