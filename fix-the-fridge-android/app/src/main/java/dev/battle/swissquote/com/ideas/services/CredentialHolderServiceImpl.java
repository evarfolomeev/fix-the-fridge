package dev.battle.swissquote.com.ideas.services;

import dev.battle.swissquote.com.ideas.data.CredentialHolder;

/**
 * Created by patrick on 03.07.14.
 */
public class CredentialHolderServiceImpl implements CredentialHolderService {
    private final CredentialHolder holder;
    private final PersistanceService persistanceService;

    public CredentialHolderServiceImpl(PersistanceService persistanceService){
        this.persistanceService = persistanceService;
        CredentialHolder tmpHolder = persistanceService.loadCredential();
        if(tmpHolder == null){
            tmpHolder = new CredentialHolder();
        }
        this.holder = tmpHolder;
    }

    public CredentialHolder getCredentialHolder(){
        return holder;
    }

}
