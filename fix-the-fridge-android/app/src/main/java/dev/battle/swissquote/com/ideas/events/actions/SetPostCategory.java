package dev.battle.swissquote.com.ideas.events.actions;

import dev.battle.swissquote.com.ideas.domain.PostCategory;

/**
 * Created by patrick on 04.07.14.
 */
public class SetPostCategory {
    public PostCategory category;

    public SetPostCategory(PostCategory category){
        this.category = category;
    }
}
