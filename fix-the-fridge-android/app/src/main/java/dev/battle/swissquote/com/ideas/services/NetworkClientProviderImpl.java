package dev.battle.swissquote.com.ideas.services;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by patrick on 03.07.14.
 */
public class NetworkClientProviderImpl implements NetworkClientProvider {

    private final OkHttpClient cachedClient;

    private final OkHttpClient nonCachedClient;

    public NetworkClientProviderImpl(Context androidContext) throws  Exception {
        cachedClient = buildCachedHttpClient(androidContext);
        nonCachedClient = buildNotCachedHttpClient();
    }

    @Override
    public OkHttpClient getCacheClient() {
        return this.cachedClient;
    }

    @Override
    public OkHttpClient getNonCacheClient() {
        return this.nonCachedClient;
    }

    private OkHttpClient buildCachedHttpClient(Context context) throws Exception {
        //10 MB or cache for HTTP requests
        Cache cache = new Cache(context.getFilesDir(), 1024 * 1024 * 10);
        OkHttpClient client = new OkHttpClient();
        client.setCache(cache);
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(15, TimeUnit.SECONDS);
        return client;
    }

    private OkHttpClient buildNotCachedHttpClient() throws Exception {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(15, TimeUnit.SECONDS);
        return client;
    }
}
