package ATINS.Microblog.service;

import ATINS.Microblog.dao.FollowerDao;
import ATINS.Microblog.dao.UzytkownikDao;
import ATINS.Microblog.dao.WiadomoscDao;
import ATINS.Microblog.model.Post;
import ATINS.Microblog.model.User;
import ATINS.Microblog.service.impl.MicroblogServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestMicroblogService {

    @Mock
    private UzytkownikDao uzytkownikDao;

    @Mock
    private FollowerDao followerDao;

    @Mock
    private WiadomoscDao wiadomoscDao;

    @InjectMocks
    private MicroblogServiceImpl microblogService;

    private User testUser;
    private Post post1;
    private Post post2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // przykładowy użytkownik
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("janek");
        testUser.setName("Jan");
        testUser.setLastname("Kowalski");
        testUser.setEmail("janek@example.com");

        // przykładowe posty
        post1 = new Post();
        post1.setId(101L);
        post1.setUser(testUser);
        post1.setContent("Pierwszy post");

        post2 = new Post();
        post2.setId(102L);
        post2.setUser(testUser);
        post2.setContent("Drugi post");
    }

    @Test
    public void testGetPostsByUser_ReturnsPosts() {
        when(wiadomoscDao.findAllByUserId(testUser.getId()))
                .thenReturn(Arrays.asList(post1, post2));

        List<Post> result = microblogService.getPostsByUser(testUser);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pierwszy post", result.get(0).getContent());

        verify(wiadomoscDao, times(1))
                .findAllByUserId(testUser.getId());
    }

    @Test
    public void testGetPostsByUser_ReturnsEmptyList() {
        when(wiadomoscDao.findAllByUserId(testUser.getId()))
                .thenReturn(Collections.emptyList());

        List<Post> result = microblogService.getPostsByUser(testUser);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(wiadomoscDao, times(1))
                .findAllByUserId(testUser.getId());
    }

    @Test
    public void testGetTimeline_ReturnsPosts() {
        when(wiadomoscDao.findFullTimelineForUser(testUser.getId()))
                .thenReturn(Arrays.asList(post1, post2));

        List<Post> result = microblogService.getTimeline(testUser);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(wiadomoscDao, times(1))
                .findFullTimelineForUser(testUser.getId());
    }

    @Test
    public void testGetTimeline_ReturnsEmptyList() {
        when(wiadomoscDao.findFullTimelineForUser(testUser.getId()))
                .thenReturn(Collections.emptyList());

        List<Post> result = microblogService.getTimeline(testUser);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(wiadomoscDao, times(1))
                .findFullTimelineForUser(testUser.getId());
    }

    @Test
    public void testAddPost_CallsDaoSave() {
        microblogService.addPost(post1);

        verify(wiadomoscDao, times(1))
                .save(post1);
    }
}