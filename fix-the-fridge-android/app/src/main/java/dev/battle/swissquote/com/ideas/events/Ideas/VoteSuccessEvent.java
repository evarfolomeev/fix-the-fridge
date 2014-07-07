package dev.battle.swissquote.com.ideas.events.Ideas;

/**
 * Created by patrick on 03.07.14.
 */
public class VoteSuccessEvent {

    private final long ideaId;

    public VoteSuccessEvent(long ideaId){
        this.ideaId = ideaId;
    }

    public long getIdeaId(){
        return ideaId;
    }
}
