package dev.battle.swissquote.com.ideas.services;

import android.content.Context;

import dev.battle.swissquote.com.ideas.data.CredentialHolder;

/**
 * Created by patrick on 04.07.14.
 */
public class PersistanceService {

    private Context context;

    public PersistanceService(Context context){
        this.context = context;
    }

    public void persistCredential(String username, String password){
        context.getSharedPreferences("credential", Context.MODE_PRIVATE).edit().putString("username", username).commit();
        context.getSharedPreferences("credential", Context.MODE_PRIVATE).edit().putString("password", password).commit();
    }

    public CredentialHolder loadCredential(){
        String storedUsername = context.getSharedPreferences("credential", Context.MODE_PRIVATE).getString("username", null);
        String storedPassword = context.getSharedPreferences("credential", Context.MODE_PRIVATE).getString("password", null);
        if(storedPassword != null && storedUsername != null){
            return new CredentialHolder(storedUsername, storedPassword);
        }
        return null;
    }
}
