package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import core.basesyntax.util.EntityManagerUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Loader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private static final Logger log = LogManager.getLogger(SmileDaoImpl.class);

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public Smile create(Smile entity) {
//        EntityManagerFactory entityManagerFactory = EntityManagerUtil.getEntityManagerFactory();
//        EntityManager entityManager = null;
//        EntityTransaction entityTransaction = null;
        Session session = null;
        Transaction transaction = null;
        try  {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
//            entityManager = entityManagerFactory.createEntityManager();
//            entityTransaction = entityManager.getTransaction();
//            transaction.begin();
//            entityManager.persist(entity);
//            entityTransaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
//            if (entityTransaction != null) {
//                entityTransaction.rollback();
//            }
            log.error("Can`t create smile to DB ");
            throw new RuntimeException("Can`t create smile to DB " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
//            if (entityManager != null) {
//                entityManager.close();
//            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Smile smile = session.get(Smile.class, id);
            return smile;
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmileQuery = session.createQuery("from Smile", Smile.class);
            return getAllSmileQuery.getResultList();
        }
    }

    @Override
    public void remove(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try  {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
//            entityManager = entityManagerFactory.createEntityManager();
//            entityTransaction = entityManager.getTransaction();
//            transaction.begin();
//            entityManager.persist(entity);
//            entityTransaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
//            if (entityTransaction != null) {
//                entityTransaction.rollback();
//            }
            log.error("Can`t delete smile to DB ");
            throw new RuntimeException("Can`t delete smile to DB " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
//            if (entityManager != null) {
//                entityManager.close();
//            }
        }
    }
}
