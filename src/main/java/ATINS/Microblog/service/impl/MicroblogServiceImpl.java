package ATINS.Microblog.service.impl;

import ATINS.Microblog.dao.FollowerDao;
import ATINS.Microblog.dao.UzytkownikDao;
import ATINS.Microblog.dao.WiadomoscDao;
import ATINS.Microblog.model.Post;
import ATINS.Microblog.model.User;
import ATINS.Microblog.service.MicroblogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroblogServiceImpl implements MicroblogService {

    @Autowired
    private UzytkownikDao uzytkownikDao;

    @Autowired
    private FollowerDao followerDao;

    @Autowired
    private WiadomoscDao wiadomoscDao;

    @Override
    public List<Post> getTimeline(User user) {
        return wiadomoscDao.findFullTimelineForUser(user.getId());
    }

    @Override
    public List<Post> getPostsByUser(User user) {
        return wiadomoscDao.findAllByUserId(user.getId());
    }

    @Override
    public void addPost(Post post) {
        wiadomoscDao.save(post);
    }
}
