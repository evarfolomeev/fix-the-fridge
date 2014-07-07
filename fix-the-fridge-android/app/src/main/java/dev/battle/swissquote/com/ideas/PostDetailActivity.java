package dev.battle.swissquote.com.ideas;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.picocontainer.PicoContainer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.events.Ideas.VoteSuccessEvent;
import dev.battle.swissquote.com.ideas.events.network.ServerErrorEvent;
import dev.battle.swissquote.com.ideas.exceptions.NotLoggedInException;
import dev.battle.swissquote.com.ideas.model.IdeaModel;
import dev.battle.swissquote.com.ideas.services.IdeaService;

/**
 * Created by patrick on 04.07.14.
 */
public class PostDetailActivity extends AbstractListActivity {

    public static final String POST_ID = "POST_ID";

    @InjectView(R.id.title)
    public TextView titleView;

    @InjectView(R.id.description)
    public TextView descriptionView;

    @InjectView(R.id.like)
    public TextView voteView;

    @InjectView(R.id.sendComment)
    public Button sendCommentButton;

    private IdeaService ideaService;

    private Long postId;

    private Idea idea;

    private IdeaModel model;

    private PicoContainer getContainer(){
        return ((FixTheFridgeApplication)getApplication()).getContainer();
    }


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.idea_detail);
        ButterKnife.inject(this);
        model = getContainer().getComponent(IdeaModel.class);
        ideaService = getContainer().getComponent(IdeaService.class);
        voteView.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     try {
                                                         ideaService.vote(getIdea());
                                                     } catch (Exception ex){
                                                         //....
                                                     }
                                                 }
                                             }

        );

    }

    public void onResume(){
        super.onResume();
        this.postId = model.currentId();
        this.idea = model.getIdea(postId);
        titleView.setText(idea.title);
        descriptionView.setText(idea.description);
        if(idea.votedByCurrentuser != null && idea.votedByCurrentuser){
            voteView.setVisibility(View.INVISIBLE);
        }
    }

    public Idea getIdea(){
        return idea;
    }

    public void onEventMainThread(VoteSuccessEvent event){
        idea.voters = idea.voters + 1;
        voteView.setVisibility(View.INVISIBLE);
    }
}
