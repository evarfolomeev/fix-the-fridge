package dev.battle.swissquote.com.ideas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.domain.PostCategory;
import dev.battle.swissquote.com.ideas.events.Ideas.LastIdeas;
import dev.battle.swissquote.com.ideas.events.Ideas.NewIdeasEvent;
import dev.battle.swissquote.com.ideas.events.actions.SelectPost;
import dev.battle.swissquote.com.ideas.model.IdeaModel;

/**
 * Created by patrick on 04.07.14.
 */
public class IdeaAdapter extends ArrayAdapter<Idea> {

    private IdeaModel ideaModel;

    private final EventBus bus;

    private Context context;

    private List<Idea> ideas = new ArrayList<Idea>();

    private PostCategory category;

    private final String commentBase;
    private final String voteBase;
    private boolean isRegistered = false;


    public IdeaAdapter(Context context, IdeaModel ideaModel, EventBus bus, PostCategory category){
        super(context, R.layout.idea_item);
        this.category = category;


        this.ideaModel = ideaModel;
        ideas = this.ideaModel.getIdeas(category);
        this.context = context.getApplicationContext();
        this.bus = bus;
        bus.register(this);
        isRegistered = true;
        this.commentBase = context.getString(R.string.comments);
        this.voteBase = context.getString(R.string.votes);
    }

    public int getCount() {
        return ideas.size();
    }

    public void setPostCategory(PostCategory category){
        this.category = category;
        this.updateList();
    }

    public Idea getItem(int position){
        return ideas.get(position);
    }

    public int getPosition(Idea item) {
        for(int i=0; i < ideas.size();i++){
            if(item.id.equals(ideas.get(i).id)){
                return i;
            }
        }
        return -1;
    }

    public long getItemId(int position) {
        return ideas.get(position).id;
    }

    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        final Idea idea = ideas.get(position);
        View v = convertView;
        if(v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.idea_item, null);
        }

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView desc = (TextView) v.findViewById(R.id.description);
        TextView votes = (TextView) v.findViewById(R.id.votes);
        TextView comments = (TextView) v.findViewById(R.id.comments);

        title.setText(idea.title);
        if(idea.description != null && idea.description.length() > 0){
            desc.setText(idea.description);
        } else {
            desc.setText("");
        }
        if(idea.voters == null || idea.voters < 0){
            idea.voters = 0;
        }
        votes.setText(voteBase+idea.voters);
        if(idea.comments == null){
            comments.setText(commentBase+0);
        } else {
            comments.setText(commentBase+idea.comments);
        }
        v.setTag(idea);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Idea clickedIdea = (Idea)view.getTag();
                ideaModel.setCurrentId(clickedIdea.id);
                bus.post(new SelectPost(clickedIdea.id));
            }
        });
        return v;
    }


    public void onEventMainThread(NewIdeasEvent event){
        if(event.category == category){
            updateList();
        }
    }

    private void updateList(){
        this.ideas = ideaModel.getIdeas(category);
        super.notifyDataSetChanged();
    }

    public void stop(){
        if(isRegistered) {
            this.bus.unregister(this);
        }
    }

    public void start(){
        if(!isRegistered) {
            this.bus.register(this);
        }
        updateList();
    }



}
