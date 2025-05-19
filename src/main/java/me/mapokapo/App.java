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
        verboseUsage();
    }

    /**
     * This method demonstrates a more verbose usage of the application.
     * It creates teams, matches, starts them, sets scores, and prints a summary.
     * It uses the repositories directly to manage teams and matches.
     */
    public static void verboseUsage() {
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
        // the `matchRepository` and `teamRepository` parameters are passed by reference
        // and they contain all the necessary data.
        Scoreboard scoreboard = new Scoreboard(matchRepository, teamRepository);

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
     * This method demonstrates a simpler and shorter usage of the application.
     * It only uses the {@link Scoreboard} class to manage matches and teams.
     */
    public static void shorthandUsage() {
        Scoreboard scoreboard = new Scoreboard(matchRepository, teamRepository);

        // Add matches
        scoreboard.addMatch("Mexico", "Canada");
        scoreboard.addMatch("Spain", "Brazil");
        scoreboard.addMatch("Germany", "France");
        scoreboard.addMatch("Uruguay", "Italy");
        scoreboard.addMatch("Argentina", "Australia");

        // Start matches
        scoreboard.startMatch(0);
        scoreboard.startMatch(1);
        scoreboard.startMatch(2);
        scoreboard.startMatch(3);
        scoreboard.startMatch(4);

        // Set scores
        scoreboard.updateScore(0, 0, 5);
        scoreboard.updateScore(1, 10, 2);
        scoreboard.updateScore(2, 2, 2);
        scoreboard.updateScore(3, 6, 6);
        scoreboard.updateScore(4, 3, 1);

        // Print summary of currently-running matches
        List<Match> summary = scoreboard.getSummary();
        printFormattedSummary(summary);

        // Finish each match
        scoreboard.finishMatch(0);
        scoreboard.finishMatch(1);
        scoreboard.finishMatch(2);
        scoreboard.finishMatch(3);
        scoreboard.finishMatch(4);

        // Print final summary (should be empty)
        summary = scoreboard.getSummary();
        System.out.println();
        printFormattedSummary(summary);
    }

    /**
     * Utility method which prints a formatted summary of the matches.
     * 
     * @param summary The summary of the matches to print.
     */
    private static void printFormattedSummary(List<Match> summary) {
        System.out.println("Match Summary:");

        if (summary.isEmpty()) {
            System.out.println("No matches found.");
            return;
        }

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
