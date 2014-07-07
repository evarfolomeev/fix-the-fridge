package dev.battle.swissquote.com.ideas.data;

/**
 * Created by patrick on 03.07.14.
 */
public class CredentialHolder {

    private String login;
    private String password;

    public CredentialHolder(String login, String password){
        this.login = login;
        this.password = password;
    }

    public CredentialHolder(){
        this.login = null;
        this.password = null;
    }

    public synchronized void set(String login, String password){
        this.login = login;
        this.password = password;
    }

    public synchronized String getLogin(){
        return login;
    }

    public synchronized String getPassword(){
        return password;
    }

    public synchronized void reset(){
        this.login = null;
        this.password = null;
    }

    public synchronized  boolean isLogguedIn(){
        return login != null && password != null;
    }
}
