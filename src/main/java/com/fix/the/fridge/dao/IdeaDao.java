package com.fix.the.fridge.dao;

import com.fix.the.fridge.domain.Idea;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class IdeaDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Idea> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Idea");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Idea> findByUser(String userNick) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Idea where user = :userNick");
		query.setParameter("userNick", userNick);
		return query.list();
	}

	public Idea findById(long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Idea) session.get(Idea.class, id);
	}

	public void save(Idea idea) {
		Session session = sessionFactory.getCurrentSession();
		session.save(idea);
	}
}
