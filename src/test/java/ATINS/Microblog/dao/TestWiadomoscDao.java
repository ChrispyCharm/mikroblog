package ATINS.Microblog.dao;

import ATINS.Microblog.dao.impl.UzytkownikDaoImpl;
import ATINS.Microblog.dao.impl.WiadomoscDaoImpl;
import ATINS.Microblog.model.Follower;
import ATINS.Microblog.model.Post;
import ATINS.Microblog.model.User;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class TestWiadomoscDao {

    private static EntityManagerFactory emf;
    private EntityManager em;

    private WiadomoscDaoImpl wiadomoscDao;
    private UzytkownikDaoImpl uzytkownikDao;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("MicroblogPU_TEST");
    }

    @AfterClass
    public static void close() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();

        wiadomoscDao = new WiadomoscDaoImpl();
        wiadomoscDao.setEntityManager(em);

        uzytkownikDao = new UzytkownikDaoImpl();
        uzytkownikDao.setEntityManager(em);

        em.getTransaction().begin();
    }

    @After
    public void tearDown() {
        em.getTransaction().rollback();
        em.close();
    }

    // ===== TEST 1 =====
    // save + findAll
    @Test
    public void testSaveAndFindAll() {
        User u = new User();
        u.setUsername("user1");
        u.setEmail("u1@test.pl");
        u.setName("User");
        u.setLastname("One");
        uzytkownikDao.save(u);

        Post p = new Post();
        p.setContent("Test post");
        p.setUser(u);
        wiadomoscDao.save(p);

        List<Post> all = wiadomoscDao.findAll();

        assertEquals(1, all.size());
        assertEquals("Test post", all.get(0).getContent());
    }

    // ===== TEST 2 =====
    // findAllByUserId
    @Test
    public void testFindAllByUserId() {
        User u = new User();
        u.setUsername("user2");
        u.setEmail("u2@test.pl");
        u.setName("User");
        u.setLastname("Two");
        uzytkownikDao.save(u);

        Post p1 = new Post();
        p1.setContent("Post 1");
        p1.setUser(u);
        wiadomoscDao.save(p1);

        Post p2 = new Post();
        p2.setContent("Post 2");
        p2.setUser(u);
        wiadomoscDao.save(p2);

        List<Post> posts = wiadomoscDao.findAllByUserId(u.getId());

        assertEquals(2, posts.size());
    }

    // ===== TEST 3 =====
    // findFullTimelineForUser
    @Test
    public void testFindFullTimelineForUser() {
        // user A
        User a = new User();
        a.setUsername("userA");
        a.setEmail("a@test.pl");
        a.setName("User");
        a.setLastname("A");
        uzytkownikDao.save(a);

        // user B
        User b = new User();
        b.setUsername("userB");
        b.setEmail("b@test.pl");
        b.setName("User");
        b.setLastname("B");
        uzytkownikDao.save(b);

        // A follows B
        Follower f = new Follower();
        f.setFollower(a);
        f.setFollowed(b);
        em.persist(f);

        // post B
        Post postB = new Post();
        postB.setContent("Post from B");
        postB.setUser(b);
        wiadomoscDao.save(postB);

        // timeline A should contain post B
        List<Post> timeline = wiadomoscDao.findFullTimelineForUser(a.getId());

        assertEquals(1, timeline.size());
        assertEquals("Post from B", timeline.get(0).getContent());
    }
}
