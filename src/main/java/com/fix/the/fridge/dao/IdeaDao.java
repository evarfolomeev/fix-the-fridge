package com.fix.the.fridge.dao;

import com.fix.the.fridge.domain.Idea;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("ideaDao")
@Transactional
public class IdeaDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Idea> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Idea");
		return (List<Idea>) query.list();
	}

	public void save(Idea idea) {
		Session session = sessionFactory.getCurrentSession();
		session.save(idea);
	}
}
