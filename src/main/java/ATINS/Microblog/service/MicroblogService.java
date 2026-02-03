package ATINS.Microblog.service;

import ATINS.Microblog.model.Post;
import ATINS.Microblog.model.User;

import java.util.List;

public interface MicroblogService {

    /**
     * Pobierz wszystkie posty użytkownika oraz posty osób, które śledzi.
     */
    List<Post> getTimeline(User user);

    /**
     * Pobierz wszystkie posty konkretnego użytkownika.
     */
    List<Post> getPostsByUser(User user);

    /**
     * Dodaj nowy post.
     */
    void addPost(Post post);
}
