package com.fix.the.fridge.dao;

import com.fix.the.fridge.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.transaction.Transactional;

/**
 * (c) Swissquote 7/3/14
 *
 * @author dpitiriakov
 */
@Repository
@Transactional
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(User user) {
		currentSession().save(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Criteria criteria = currentSession().createCriteria(User.class);
		List<User> users = (List<User>) criteria.list();
		return users;
	}

	public void update(User user) {
		currentSession().update(user);
	}

	public void remove(User user) {
		currentSession().delete(user);
	}

	public User find(String nickName) {
		return (User) currentSession().get(User.class, nickName);
	}





}
