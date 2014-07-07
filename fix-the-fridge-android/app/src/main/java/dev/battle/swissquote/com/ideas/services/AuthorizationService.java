package dev.battle.swissquote.com.ideas.services;

import com.squareup.okhttp.Request;

/**
 * Created by patrick on 03.07.14.
 */
public interface AuthorizationService {
    public void addAuthToRequest(Request.Builder requestBuilder);
}
