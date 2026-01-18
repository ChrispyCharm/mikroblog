package ATINS.Microblog.dao.impl;

import ATINS.Microblog.dao.FollowerDao;
import ATINS.Microblog.model.Follower;
import ATINS.Microblog.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class FollowerDaoImpl implements FollowerDao {

    @PersistenceContext
    private EntityManager entityManager;
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void follow(long followerId, long followedId) {
        User follower = entityManager.find(User.class, followerId);
        User followed = entityManager.find(User.class, followedId);

        Follower relation = new Follower();
        relation.setFollower(follower);
        relation.setFollowed(followed);

        entityManager.persist(relation);
    }

    @Override
    public void unfollow(long followerId, long followedId) {
        TypedQuery<Follower> query = entityManager.createQuery(
                "SELECT f FROM Follower f WHERE f.follower.id = :followerId AND f.followed.id = :followedId",
                Follower.class
        );
        query.setParameter("followerId", followerId);
        query.setParameter("followedId", followedId);

        query.getResultList()
             .forEach(entityManager::remove);
    }

    @Override
    public boolean isFollowing(long followerId, long followedId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(f) FROM Follower f WHERE f.follower.id = :followerId AND f.followed.id = :followedId",
                Long.class
        );
        query.setParameter("followerId", followerId);
        query.setParameter("followedId", followedId);

        return query.getSingleResult() > 0;
    }
}
