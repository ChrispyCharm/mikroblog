package ATINS.Microblog.dao;

import java.util.List;

public interface WiadomoscDao {

    /**
     * Returns all posts created by a specific user.
     */
    List<Post> findAllByUserId(long userId);

    /**
     * Returns full timeline for a user:
     * user's posts and posts from users he follows.
     */
    List<Post> findFullTimelineForUser(long userId);

    /**
     * Returns public timeline:
     * posts from all users.
     */
    List<Post> findAll();

    /**
     * Saves a new post.
     */
    void save(Post post);
}
