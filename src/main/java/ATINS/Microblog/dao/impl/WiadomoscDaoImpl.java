package ATINS.Microblog.dao.impl;

import ATINS.Microblog.dao.WiadomoscDao;
import ATINS.Microblog.model.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class WiadomoscDaoImpl implements WiadomoscDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Post> findAllByUserId(long userId) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.createdAt DESC",
                Post.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Post> findFullTimelineForUser(long userId) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post p " +
                "WHERE p.user.id = :userId " +
                "OR p.user.id IN (" +
                "   SELECT f.followed.id FROM Follower f WHERE f.follower.id = :userId" +
                ") " +
                "ORDER BY p.createdAt DESC",
                Post.class
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Post> findAll() {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post p ORDER BY p.createdAt DESC",
                Post.class
        );
        return query.getResultList();
    }

    @Override
    public void save(Post post) {
        entityManager.persist(post);
    }
}
