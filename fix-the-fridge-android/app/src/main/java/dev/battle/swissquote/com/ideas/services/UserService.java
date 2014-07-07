package dev.battle.swissquote.com.ideas.services;

import dev.battle.swissquote.com.ideas.domain.User;

/**
 * Created by patrick on 03.07.14.
 */
public interface UserService {

   void signUp(String email, String nickname, String password);

   void login(String username, String password);

}
