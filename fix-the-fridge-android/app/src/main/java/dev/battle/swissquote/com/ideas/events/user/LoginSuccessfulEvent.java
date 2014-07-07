package dev.battle.swissquote.com.ideas.events.user;

/**
 * Created by patrick on 03.07.14.
 */
public class LoginSuccessfulEvent {
    private String login;

    public LoginSuccessfulEvent(String login){
        this.login = login;
    }

    public String getLogin(){
        return login;
    }
}
