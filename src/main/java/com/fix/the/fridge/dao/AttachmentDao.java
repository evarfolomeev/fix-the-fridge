package com.fix.the.fridge.dao;

import com.fix.the.fridge.domain.Attachment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Atrem Petrov
 */
@Repository
@Transactional
public class AttachmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Attachment attachment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(attachment);
    }

    public Attachment get(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (Attachment) session.get(Attachment.class, id);
    }
}
