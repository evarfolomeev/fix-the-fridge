package com.fix.the.fridge.dao;

import com.fix.the.fridge.domain.Comment;
import com.fix.the.fridge.domain.Idea;
import com.fix.the.fridge.domain.Comment;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class CommentDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Comment comment) {
		currentSession().save(comment);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findAll() {
		Criteria criteria = currentSession().createCriteria(Comment.class);
		List<Comment> comments = (List<Comment>) criteria.list();
		return comments;
	}

	public void update(Comment comment) {
		currentSession().update(comment);
	}

	public void remove(Comment comment) {
		currentSession().delete(comment);
	}

	public Comment findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return (Comment) session.get(Comment.class, id);
	}
}
