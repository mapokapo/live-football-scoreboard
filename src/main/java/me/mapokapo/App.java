package me.mapokapo;

import java.util.List;

import me.mapokapo.features.matches.Match;
import me.mapokapo.features.matches.MatchRepository;
import me.mapokapo.features.scoreboard.Scoreboard;
import me.mapokapo.features.teams.Team;
import me.mapokapo.features.teams.TeamRepository;

public class App {
    private static final TeamRepository teamRepository = new TeamRepository();
    private static final MatchRepository matchRepository = new MatchRepository();

    public static void main(String[] args) {
        // Define teams
        var mexico = teamRepository.addTeam(new Team(0, "Mexico"));
        var canada = teamRepository.addTeam(new Team(1, "Canada"));
        var spain = teamRepository.addTeam(new Team(2, "Spain"));
        var brazil = teamRepository.addTeam(new Team(3, "Brazil"));
        var germany = teamRepository.addTeam(new Team(4, "Germany"));
        var france = teamRepository.addTeam(new Team(5, "France"));
        var uruguay = teamRepository.addTeam(new Team(6, "Uruguay"));
        var italy = teamRepository.addTeam(new Team(7, "Italy"));
        var argentina = teamRepository.addTeam(new Team(8, "Argentina"));
        var australia = teamRepository.addTeam(new Team(9, "Australia"));

        // Create matches
        var match1 = matchRepository.addMatch(new Match(0, mexico, canada));
        var match2 = matchRepository.addMatch(new Match(1, spain, brazil));
        var match3 = matchRepository.addMatch(new Match(2, germany, france));
        var match4 = matchRepository.addMatch(new Match(3, uruguay, italy));
        var match5 = matchRepository.addMatch(new Match(4, argentina, australia));

        // Start matches
        match1.start();
        match2.start();
        match3.start();
        match4.start();
        match5.start();

        // Set scores
        match1.setScore(0, 5);
        match2.setScore(10, 2);
        match3.setScore(2, 2);
        match4.setScore(6, 6);
        match5.setScore(3, 1);

        // The scoreboard can either be created before or after team and match creation:
        // the `matchRepository` variable is passed by reference and it contains all the
        // necessary data.
        Scoreboard scoreboard = new Scoreboard(matchRepository);

        // Print summary of currently-running matches
        List<Match> summary = scoreboard.getSummary();
        printFormattedSummary(summary);

        // Finish each match
        scoreboard.finishMatch(match1.getId());
        scoreboard.finishMatch(match2.getId());
        scoreboard.finishMatch(match3.getId());
        scoreboard.finishMatch(match4.getId());
        scoreboard.finishMatch(match5.getId());
    }

    /**
     * Utility method which prints a formatted summary of the matches.
     * 
     * @param summary The summary of the matches to print.
     */
    private static void printFormattedSummary(List<Match> summary) {
        System.out.println("Match Summary:");
        for (int i = 0; i < summary.size(); i++) {
            var match = summary.get(i);
            System.out.printf(
                    "%d. %s %d - %s %d\n",
                    i + 1,
                    match.getHomeTeam().getName(),
                    match.getHomeScore(),
                    match.getAwayTeam().getName(),
                    match.getAwayScore());
        }

    }
}
