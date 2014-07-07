package dev.battle.swissquote.com.ideas.services;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import de.greenrobot.event.EventBus;
import dev.battle.swissquote.com.ideas.config.NetworkConfig;
import dev.battle.swissquote.com.ideas.domain.User;
import dev.battle.swissquote.com.ideas.events.network.NetworkErrorEvent;
import dev.battle.swissquote.com.ideas.events.network.NotAuthorizedEvent;
import dev.battle.swissquote.com.ideas.events.network.ServerErrorEvent;
import dev.battle.swissquote.com.ideas.events.user.LoginSuccessfulEvent;
import dev.battle.swissquote.com.ideas.events.user.SignupSuccessfulEvent;
import okio.BufferedSink;

/**
 * Created by patrick on 03.07.14.
 */
public class UserServiceImpl implements UserService {

    private static final String USER_PATH = "/user";

    public static  final String LOGIN = "/signup";

    public static  final String SIGN_UP = "/do-sign-up";

    private final CredentialHolderService credentialHolderService;
    private final NetworkClientProvider networkClientProvider;
    private final AuthorizationService authorizationService;
    private final PersistanceService persistanceService;
    private final EventBus bus;
    private final Gson gson;

    public UserServiceImpl(EventBus bus, NetworkClientProvider networkClientProvider, AuthorizationService authorizationService, CredentialHolderService credentialHolderService, Gson gson, PersistanceService persistanceService){
        this.bus = bus;
        this.networkClientProvider = networkClientProvider;
        this.authorizationService = authorizationService;
        this.credentialHolderService = credentialHolderService;
        this.persistanceService = persistanceService;
        this.gson = gson;
    }



    public void login(final String username, String password){
        credentialHolderService.getCredentialHolder().set(username, password);
        OkHttpClient httpClient = networkClientProvider.getNonCacheClient();
        Request loginReq = getLoginRequest();
        httpClient.newCall(loginReq).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                bus.post(new NetworkErrorEvent());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    if(response.code() == 200){
                        bus.post(new LoginSuccessfulEvent(username));
                        return;
                    } else if (response.code() == 401){
                        //Clean the credentials !
                        credentialHolderService.getCredentialHolder().reset();
                        bus.post(new NotAuthorizedEvent());
                        return;
                    }
                }
                bus.post(new ServerErrorEvent());
            }
        });
    }

    public Request getLoginRequest(){
        Request.Builder builder = new Request.Builder().get();
        String url = getLoginUrl();
        builder.url(url);
        authorizationService.addAuthToRequest(builder);
        Request request = builder.build();
        return request;
    }

    public String getLoginUrl(){
        return new StringBuilder(NetworkConfig.BASE_URL).append(USER_PATH).append(LOGIN).toString();
    }




    public void signUp(final String email, final String nickname, final String password) {

        networkClientProvider.getNonCacheClient().newCall(getSignupRequest(email, nickname, password)).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                bus.post(new NetworkErrorEvent());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful() && response.code() == 200){
                    credentialHolderService.getCredentialHolder().set(nickname, password);
                    persistanceService.persistCredential(nickname, password);
                    bus.post(new SignupSuccessfulEvent());
                    return;
                } else if(response.code() == 401){
                    bus.post(new NotAuthorizedEvent());
                } else {
                    bus.post(new ServerErrorEvent());
                }
            }
        });
    }

    //TODO : find a nice way to send the password !
    //Signup !
    public Request getSignupRequest(final String email, final String nickname, final String password){
        Request.Builder builder = new Request.Builder().post(new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/x-www-form-urlencoded");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeString(getSignUpFormData(email, nickname, password), Charset.defaultCharset());
            }
        });
        String url = getSignupUrl();
        builder.url(url);
        Request request = builder.build();
        return request;
    }

    public String getSignupUrl(){
        return new StringBuilder(NetworkConfig.BASE_URL).append(USER_PATH).append(SIGN_UP).toString();
    }

    public String getSignUpFormData(final String email, String nickname, String password) {
        try {
            return new StringBuilder("nickname=").append(URLEncoder.encode(nickname)).append("&email=").append(URLEncoder.encode(email)).append("&password=").append(URLEncoder.encode(password)).toString();
        } catch (Exception ex) {
            // Nothing
        }
        return null;
    }
}
