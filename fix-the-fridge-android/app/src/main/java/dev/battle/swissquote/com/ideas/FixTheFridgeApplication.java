package dev.battle.swissquote.com.ideas;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.PicoContainer;
import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.model.IdeaModelImpl;
import dev.battle.swissquote.com.ideas.services.BasicAuthAuthorizationServiceImpl;
import dev.battle.swissquote.com.ideas.services.CredentialHolderService;
import dev.battle.swissquote.com.ideas.services.CredentialHolderServiceImpl;
import dev.battle.swissquote.com.ideas.services.IdeaServiceImpl;
import dev.battle.swissquote.com.ideas.services.NetworkClientProviderImpl;
import dev.battle.swissquote.com.ideas.services.PersistanceService;
import dev.battle.swissquote.com.ideas.services.UserServiceImpl;

/**
 * Created by patrick on 01.07.14.
 */
public class FixTheFridgeApplication extends Application {

    DefaultPicoContainer container = new DefaultPicoContainer();

    /**
     * @return the initialized PicoContainer
     */
    public PicoContainer getContainer(){
        return container;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        try {
            setupContainer();
        } catch (Exception ex){
            Log.i("FixTheFridgeApplication", "can not setup all components", ex);
        }
    }

    /**
     * Put here all the instances of services required
     */
    public void setupContainer() throws Exception {
        //Add a component for the Bus with UI thread
        container.addComponent(new EventBus());

        //Add Gson builder
        container.addComponent(Gson.class, new GsonBuilder().serializeNulls().create());

        //Add the NetworkClientProvider
        container.as(Characteristics.CACHE).addComponent(new NetworkClientProviderImpl(getApplicationContext()));

        //Some services !
        //Idea service
        container.as(Characteristics.CACHE).addComponent(new PersistanceService(getApplicationContext()));
        container.as(Characteristics.CACHE).addComponent(CredentialHolderServiceImpl.class);
        container.as(Characteristics.CACHE).addComponent(BasicAuthAuthorizationServiceImpl.class);

        container.as(Characteristics.CACHE).addComponent(IdeaServiceImpl.class);
        container.as(Characteristics.CACHE).addComponent(UserServiceImpl.class);
        container.as(Characteristics.CACHE).addComponent(IdeaModelImpl.class);
    }
}
