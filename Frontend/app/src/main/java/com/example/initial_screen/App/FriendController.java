package com.example.initial_screen.App;

public class FriendController {
    private String username;

    /**
     * New friendcontroller for the user
     * @param username takes in a username
     */
    public FriendController(String username) {
        this.username = username;
    }

    /**
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
