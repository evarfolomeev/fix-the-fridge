package dev.battle.swissquote.com.ideas;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.picocontainer.PicoContainer;

import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.events.UnexpectedError;
import dev.battle.swissquote.com.ideas.events.network.NetworkErrorEvent;
import dev.battle.swissquote.com.ideas.events.network.NotAuthorizedEvent;
import dev.battle.swissquote.com.ideas.events.network.ServerErrorEvent;
import dev.battle.swissquote.com.ideas.services.CredentialHolderService;

/**
 * Created by patrick on 04.07.14.
 */
public class AbstractListActivity extends ListActivity {

    protected EventBus bus;

    protected PicoContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container = ((FixTheFridgeApplication)getApplication()).getContainer();
        bus = container.getComponent(EventBus.class);
    }

    public void onResume(){
        super.onResume();
        bus.register(this);
    }

    public void onPause(){
        bus.unregister(this);
        super.onPause();
    }


    public void onEventMainThread(UnexpectedError event){
        Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(NetworkErrorEvent event){
        Toast.makeText(getApplicationContext(), "Network problems", Toast.LENGTH_SHORT).show();
    }


    public void onEventMainThread(NotAuthorizedEvent event){
        //Please do not look at the next 2 lines of code
        container.getComponent(CredentialHolderService.class).getCredentialHolder().reset();
        Toast.makeText(getApplicationContext(), "Not authorized", Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(ServerErrorEvent event){
        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
    }

}
