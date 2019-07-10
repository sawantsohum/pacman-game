package com.example.initial_screen.App;

public class FriendSearchController {
    private int rank;
    private int hscore;
    private String username;

    /**
     *
     * @param rank takes in a users rank
     * @param hscore takes in a users highscore
     * @param username takes in a users username
     */
    public FriendSearchController(int rank, int hscore, String username) {
        this.rank = rank;
        this.hscore = hscore;
        this.username = username;
    }

    /**
     *
     * @return the users rank
     */
    public int getRank() {
        return rank;
    }

    /**
     *
     * @return the users highscore
     */
    public int getHscore() {
        return hscore;
    }

    /**
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }
}

