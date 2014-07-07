package dev.battle.swissquote.com.ideas.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.domain.PostCategory;
import dev.battle.swissquote.com.ideas.events.Ideas.LastIdeas;
import dev.battle.swissquote.com.ideas.events.Ideas.NewIdeasEvent;
import dev.battle.swissquote.com.ideas.events.network.ServerErrorEvent;

/**
 * Created by patrick on 04.07.14.
 */
public class IdeaModelImpl implements IdeaModel {

    private Map<PostCategory, List<Idea>> data = new HashMap<PostCategory, List<Idea>>();

    private Map<Long, Idea> dataPerId = new HashMap<Long, Idea>();

    private Long currentId = null;

    private EventBus bus;

    public IdeaModelImpl(EventBus bus){
        for(PostCategory category : PostCategory.values()){
            data.put(category, new ArrayList<Idea>());
        }
        this.bus = bus;
        this.bus.register(this);
    }

    public List<Idea> getIdeas(PostCategory category){
        return data.get(category);
    }

    public void onEventMainThread(LastIdeas event){
        PostCategory category = event.category;
        if(event.ideas == null){
            this.data.put(category, new ArrayList<Idea>());
        } else {
            this.data.put(category, new ArrayList<Idea>(event.ideas));
        }
        for(Idea idea : event.ideas){
            this.dataPerId.put(idea.id, idea);
        }
        bus.post(new NewIdeasEvent(category));
    }

    public Idea getIdea(long id){
        return dataPerId.get(id);
    }

    public Long currentId(){
        return currentId;
    }

    public void setCurrentId(Long id){
        this.currentId = id;
    }


}
