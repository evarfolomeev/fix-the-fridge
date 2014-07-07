package dev.battle.swissquote.com.ideas.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.config.NetworkConfig;
import dev.battle.swissquote.com.ideas.data.CredentialHolder;
import dev.battle.swissquote.com.ideas.domain.Comment;
import dev.battle.swissquote.com.ideas.domain.Idea;
import dev.battle.swissquote.com.ideas.domain.PostCategory;
import dev.battle.swissquote.com.ideas.domain.Status;
import dev.battle.swissquote.com.ideas.events.Ideas.LastIdeas;
import dev.battle.swissquote.com.ideas.events.Ideas.PostIdeaSuccess;
import dev.battle.swissquote.com.ideas.events.Ideas.VoteSuccessEvent;
import dev.battle.swissquote.com.ideas.events.UnexpectedError;
import dev.battle.swissquote.com.ideas.events.network.NetworkErrorEvent;
import dev.battle.swissquote.com.ideas.events.network.NotAuthorizedEvent;
import dev.battle.swissquote.com.ideas.events.network.ServerErrorEvent;
import dev.battle.swissquote.com.ideas.exceptions.NotLoggedInException;
import okio.BufferedSink;

/**
 * Created by patrick on 03.07.14.
 */
public class IdeaServiceImpl implements IdeaService {

    private final Gson gson;
    private final NetworkClientProvider networkClientProvider;
    private final EventBus bus;
    private final AuthorizationService authService;

    private CredentialHolderService credentialHolderService;
    private Type ideaListType = new TypeToken<ArrayList<Idea>>() {}.getType();

    private static final String IDEA_PATH = "/idea";

    private static final String ALL_NEWEST = "/get-all-newest";

    private static final String ALL_HOTEST = "/get-all-hottest";

    private static final String VOTE = "/vote";

    private static final String SAVE = "/save";

    public IdeaServiceImpl(Gson gson, NetworkClientProvider networkClientProvider, EventBus bus, AuthorizationService authService, CredentialHolderService credentialHolderService){
        this.gson = gson;
        this.networkClientProvider = networkClientProvider;
        this.bus = bus;
        this.authService = authService;
        this.credentialHolderService = credentialHolderService;
    }

    //FETCH IDEAS PART

    @Override
    public void fetchLatestIdeas(final PostCategory category) {

        networkClientProvider.getNonCacheClient().newCall(getAllRequest(category)).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                bus.post(new NetworkErrorEvent());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        List<Idea> ideas = extractIdeas(response);
                        LastIdeas event = new LastIdeas();
                        event.ideas = ideas;
                        event.category = category;
                        bus.post(event);
                        return;
                    } else if (response.code() == 401) {
                        bus.post(new NotAuthorizedEvent());
                        return;
                    } else {
                        bus.post(new ServerErrorEvent());
                        return;
                    }
                } catch (Exception e){
                    Log.i("IdeaServiceImpl", "error",e);
                    bus.post(new UnexpectedError());
                }
            }
        });
    }

    private Request getAllRequest(PostCategory category){
        Request.Builder builder = new Request.Builder().get();
        authService.addAuthToRequest(builder);
        builder.url(getAllUrl(category));
        return builder.build();
    }

    private String getAllUrl(PostCategory category){
        switch(category){
            case HOTTEST:
                return new StringBuilder(NetworkConfig.BASE_URL).append(IDEA_PATH).append(ALL_HOTEST).toString();
            case NEWEST:
                return new StringBuilder(NetworkConfig.BASE_URL).append(IDEA_PATH).append(ALL_NEWEST).toString();
            default:
                throw new IllegalStateException("Unknown category "+category);

        }
    }

    //VOTE PART

    @Override
    public void vote(final Idea idea) throws NotLoggedInException {
        CredentialHolder credentialHolder = credentialHolderService.getCredentialHolder();

        if(!credentialHolder.isLogguedIn()){
            throw new NotLoggedInException();
        }

        networkClientProvider.getNonCacheClient().newCall(getVoteUp(idea)).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                bus.post(new NetworkErrorEvent());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    if (response.isSuccessful() && response.code() == 200) {
                        bus.post(new VoteSuccessEvent(idea.id));
                        return;
                    } else if (response.code() == 401) {
                        bus.post(new NotAuthorizedEvent());
                        return;
                    } else {
                        Log.i("Error sending data", response.body().string());
                        bus.post(new ServerErrorEvent());
                        return;
                    }
                } catch (Exception e){
                    Log.i("IdeaServiceImpl", "error",e);
                    bus.post(new UnexpectedError());
                }
            }
        });
    }

    private Request getVoteUp(final Idea idea){
        Request.Builder builder = new Request.Builder().post(new RequestBody() {
            @Override
            public MediaType contentType() {
              return MediaType.parse("application/x-www-form-urlencoded");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeString("ideaId="+idea.id, Charset.defaultCharset());
            }
        });
        authService.addAuthToRequest(builder);
        builder.url(voteUpUrl(idea));
        return builder.build();
    }

    private String voteUpUrl(Idea idea){
        CredentialHolder credentialHolder = credentialHolderService.getCredentialHolder();
        return new StringBuilder(NetworkConfig.BASE_URL).append(IDEA_PATH).//
                append(VOTE).
                toString();

    }

    //POST IDEA part

    @Override
    public void sendIdea(final Idea idea) throws NotLoggedInException {
        CredentialHolder credentialHolder = credentialHolderService.getCredentialHolder();


        if(!credentialHolder.isLogguedIn()){
            throw new NotLoggedInException();
        }

        networkClientProvider.getNonCacheClient().newCall(getPostIdeaRequest(idea)).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                bus.post(new NetworkErrorEvent());
                return;
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    Log.e("IdeaServiceImpl", "response code " + response.code());
                    if (response.isSuccessful() && response.code() == 200) {
                        bus.post(new PostIdeaSuccess(idea.title));
                        return;
                    } else if (response.code() == 401) {
                        bus.post(new NotAuthorizedEvent());
                        return;
                    } else {
                        bus.post(new ServerErrorEvent());
                        return;
                    }
                } catch (Exception e){
                    Log.e("IdeaServiceImpl", "unexpected error ", e);
                    bus.post(new UnexpectedError());
                    return;
                }
            }
        });
    }

    private Request getPostIdeaRequest(final Idea idea){
        Request.Builder builder = new Request.Builder().post(new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/json");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
               //Log.i("IdeaServiceImpl", "encoded idea : "+gson.toJson(idea));
                String data = "{\"title\":\""+idea.title+"\",\"description\":\""+idea.description+"\"}";
               sink.writeString(data, Charset.defaultCharset());
            }
        });
        authService.addAuthToRequest(builder);
        builder.url(postIdeaUrl(idea));
        return builder.build();
    }

    private String postIdeaUrl(Idea idea){
        idea.votedByCurrentuser = false;
        idea.status = Status.OPEN;
        CredentialHolder credentialHolder = credentialHolderService.getCredentialHolder();
        return new StringBuilder(NetworkConfig.BASE_URL).append(IDEA_PATH).//
                append(SAVE).toString();
    }


    private List<Idea> extractIdeas(Response response) throws Exception {
        String responseContent = response.body().string();
        return gson.fromJson(responseContent, ideaListType);
    }

}
