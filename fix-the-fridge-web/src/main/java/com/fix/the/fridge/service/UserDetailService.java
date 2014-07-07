package com.fix.the.fridge.service;

import com.fix.the.fridge.dao.UserDao;
import com.fix.the.fridge.domain.UserRole;
import com.fix.the.fridge.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * (c) Swissquote 7/3/14
 *
 * @author evarfolomeev
 */
@Transactional(readOnly = true)
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final boolean accountNonExpired = true;
		final boolean credentialsNonExpired = true;
		final boolean accountNonLocked = true;

		User user = userDao.find(username);

		return new org.springframework.security.core.userdetails.User(
				user.getNickName(),
				user.getPassword(),
				user.isEnabled(),
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(user)
		);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		for (UserRole userRole : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(userRole.toString()));
		}
		return authorities;
	}
}
