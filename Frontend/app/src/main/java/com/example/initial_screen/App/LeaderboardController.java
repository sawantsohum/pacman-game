package com.example.initial_screen.App;

public class LeaderboardController {

    private int rank;
    private String username;
    private int hScore;

    /**
     *
     * @param rank a users rank
     * @param username a users username
     * @param hScore a users highscore
     */
    public LeaderboardController(int rank, String username, int hScore) {
        this.rank = rank;
        this.username = username;
        this.hScore = hScore;
    }


    /**
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the highscore
     */
    public int gethScore() {
        return hScore;
    }
}
