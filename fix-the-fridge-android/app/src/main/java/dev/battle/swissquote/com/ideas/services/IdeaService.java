package dev.battle.swissquote.com.ideas.services;

import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.domain.PostCategory;
import dev.battle.swissquote.com.ideas.exceptions.NotLoggedInException;

/**
 * Created by patrick on 03.07.14.
 */
public interface IdeaService {

    public void fetchLatestIdeas(PostCategory category);


    /**
     * Throws a NotLoggedInException if the user it not logged in
     * @param idea
     * @throws NotLoggedInException
     */
    public void vote(Idea idea) throws NotLoggedInException;

    /**
     * Throws a NotLoggedInException if the user it not logged in
     * @param idea
     * @throws NotLoggedInException
     */
    public void sendIdea(Idea idea) throws NotLoggedInException;
}
