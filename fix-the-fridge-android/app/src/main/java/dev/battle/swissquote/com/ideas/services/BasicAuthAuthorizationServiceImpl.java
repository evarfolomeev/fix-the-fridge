package dev.battle.swissquote.com.ideas.services;

import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;

import dev.battle.swissquote.com.ideas.data.CredentialHolder;

/**
 * Created by patrick on 03.07.14.
 */
public class BasicAuthAuthorizationServiceImpl implements AuthorizationService {

    public static final String AUTH_HEADER = "Authorization";

    private CredentialHolderService credentialHolderService;

    public BasicAuthAuthorizationServiceImpl(CredentialHolderService credentialHolderService){
        this.credentialHolderService = credentialHolderService;
    }

    @Override
    public void addAuthToRequest(Request.Builder requestBuilder) {
        CredentialHolder holder = credentialHolderService.getCredentialHolder();
        if (holder != null && holder.isLogguedIn()) {
            requestBuilder.addHeader(AUTH_HEADER, getCredentialEncoded(holder));
        }
    }

    public String getCredentialEncoded(CredentialHolder holder) {
            String username = holder.getLogin();
            String password = holder.getPassword();
            return Credentials.basic(username, password);

    }
}
