package ATINS.Microblog.dao;

import ATINS.Microblog.dao.impl.FollowerDaoImpl;
import ATINS.Microblog.dao.impl.UzytkownikDaoImpl;
import ATINS.Microblog.model.User;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class TestFollowerDao {

    private static EntityManagerFactory emf;
    private EntityManager em;

    private FollowerDaoImpl followerDao;
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

        followerDao = new FollowerDaoImpl();
        followerDao.setEntityManager(em);

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
    // follow() + isFollowing()
    @Test
    public void testFollowUser() {
        User a = new User();
        a.setUsername("userA");
        a.setEmail("a@test.pl");
        a.setName("User");
        a.setLastname("A");
        uzytkownikDao.save(a);

        User b = new User();
        b.setUsername("userB");
        b.setEmail("b@test.pl");
        b.setName("User");
        b.setLastname("B");
        uzytkownikDao.save(b);

        followerDao.follow(a.getId(), b.getId());

        assertTrue(followerDao.isFollowing(a.getId(), b.getId()));
    }

    // ===== TEST 2 =====
    // unfollow()
    @Test
    public void testUnfollowUser() {
        User a = new User();
        a.setUsername("userA2");
        a.setEmail("a2@test.pl");
        a.setName("User");
        a.setLastname("A2");
        uzytkownikDao.save(a);

        User b = new User();
        b.setUsername("userB2");
        b.setEmail("b2@test.pl");
        b.setName("User");
        b.setLastname("B2");
        uzytkownikDao.save(b);

        followerDao.follow(a.getId(), b.getId());
        assertTrue(followerDao.isFollowing(a.getId(), b.getId()));

        followerDao.unfollow(a.getId(), b.getId());

        assertFalse(followerDao.isFollowing(a.getId(), b.getId()));
    }

    // ===== TEST 3 =====
    // isFollowing() gdy brak relacji
    @Test
    public void testIsFollowingFalse() {
        User a = new User();
        a.setUsername("userA3");
        a.setEmail("a3@test.pl");
        a.setName("User");
        a.setLastname("A3");
        uzytkownikDao.save(a);

        User b = new User();
        b.setUsername("userB3");
        b.setEmail("b3@test.pl");
        b.setName("User");
        b.setLastname("B3");
        uzytkownikDao.save(b);

        assertFalse(followerDao.isFollowing(a.getId(), b.getId()));
    }
}
