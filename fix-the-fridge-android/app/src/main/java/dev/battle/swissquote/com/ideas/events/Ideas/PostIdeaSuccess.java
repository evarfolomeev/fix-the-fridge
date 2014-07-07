package dev.battle.swissquote.com.ideas.events.Ideas;

/**
 * Created by patrick on 03.07.14.
 */
public class PostIdeaSuccess {
    private final String title;

    public PostIdeaSuccess(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
