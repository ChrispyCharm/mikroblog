package ATINS.Microblog.dao;

import java.util.Optional;

public interface UzytkownikDao {

    /**
     * Returns a user by username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Registers / saves a new user.
     */
    void save(User user);
}
