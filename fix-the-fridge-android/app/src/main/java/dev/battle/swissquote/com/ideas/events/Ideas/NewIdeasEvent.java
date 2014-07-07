package dev.battle.swissquote.com.ideas.events.Ideas;

import dev.battle.swissquote.com.ideas.domain.PostCategory;

/**
 * Created by patrick on 04.07.14.
 */
public class NewIdeasEvent {
    public PostCategory category;

    public NewIdeasEvent(PostCategory category){
        this.category = category;
    }
}
