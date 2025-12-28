package ATINS.Microblog.dao;

public interface FollowerDao {

    /**
     * Adds a user to the followed list.
     */
    void follow(long followerId, long followedId);

    /**
     * Removes a user from the followed list.
     */
    void unfollow(long followerId, long followedId);

    /**
     * Checks whether a user follows another user.
     */
    boolean isFollowing(long followerId, long followedId);
}
