package dev.battle.swissquote.com.ideas.services;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by patrick on 03.07.14.
 */
public interface NetworkClientProvider {

    OkHttpClient getCacheClient();

    OkHttpClient getNonCacheClient();
}
