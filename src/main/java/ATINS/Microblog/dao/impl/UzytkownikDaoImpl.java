package ATINS.Microblog.dao.impl;

import ATINS.Microblog.dao.UzytkownikDao;
import ATINS.Microblog.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class UzytkownikDaoImpl implements UzytkownikDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username",
                User.class
        );
        query.setParameter("username", username);

        return query.getResultList().stream().findFirst();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
}
