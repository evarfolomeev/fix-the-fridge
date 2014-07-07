package dev.battle.swissquote.com.ideas.model;

import java.util.List;

import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.domain.PostCategory;

/**
 * Created by patrick on 04.07.14.
 */
public interface IdeaModel {

    //Get
    public List<Idea> getIdeas(PostCategory category);

    public Idea getIdea(long id);

    public Long currentId();

    public void setCurrentId(Long id);

}
