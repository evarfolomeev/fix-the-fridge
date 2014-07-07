package dev.battle.swissquote.com.ideas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.picocontainer.PicoContainer;

import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.domain.PostCategory;
import dev.battle.swissquote.com.ideas.events.actions.RefreshEvent;
import dev.battle.swissquote.com.ideas.events.actions.SelectPost;
import dev.battle.swissquote.com.ideas.events.actions.SetPostCategory;
import dev.battle.swissquote.com.ideas.events.user.SignupSuccessfulEvent;
import dev.battle.swissquote.com.ideas.model.IdeaModel;
import dev.battle.swissquote.com.ideas.services.IdeaService;

/**
 * Created by patrick on 04.07.14.
 */
public class PostListFragment extends ListFragment {


    public PostCategory category = PostCategory.NEWEST;

    private IdeaAdapter ideaAdapter;

    private EventBus bus;

    private IdeaService ideaService;

    private PicoContainer getContainer(){
        return ((FixTheFridgeApplication)getActivity().getApplication()).getContainer();
    }

    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ideaService = getContainer().getComponent(IdeaService.class);
        this.bus = getContainer().getComponent(EventBus.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.post_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ideaAdapter = new IdeaAdapter(this.getActivity(), getContainer().getComponent(IdeaModel.class), getContainer().getComponent(EventBus.class), category);
        setListAdapter(ideaAdapter);
        ideaAdapter.setPostCategory(category);
    }

    @Override
    public void onPause(){
        super.onStop();
        this.bus.unregister(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.bus.register(this);
        this.ideaService.fetchLatestIdeas(category);
    }

    public void onEventMainThread(RefreshEvent event) {
        ideaService.fetchLatestIdeas(category);
    }

    public void onEventMainThread(SetPostCategory event) {
        category = event.category;
        this.ideaAdapter.setPostCategory(category);
        ideaService.fetchLatestIdeas(category);
    }

    public void onEventMainThread(SelectPost event) {
        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
        startActivity(intent);
    }


}
