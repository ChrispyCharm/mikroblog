package ATINS.Microblog.dao;

import ATINS.Microblog.dao.impl.UzytkownikDaoImpl;
import ATINS.Microblog.model.User;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestUzytkownikDao {

    private static EntityManagerFactory emf;
    private EntityManager em;
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
    @Test
    public void testSaveAndFindByUsername() {
        User u = new User();
        u.setUsername("jakub");
        u.setEmail("jakub@test.pl");
        u.setName("Jakub");
        u.setLastname("Wojtasik");

        uzytkownikDao.save(u);

        Optional<User> found = uzytkownikDao.findByUsername("jakub");

        assertTrue(found.isPresent());
        assertEquals("jakub@test.pl", found.get().getEmail());
    }

    // ===== TEST 2 =====
    @Test
    public void testFindByUsernameNotExisting() {
        Optional<User> found = uzytkownikDao.findByUsername("nieistnieje");
        assertFalse(found.isPresent());
    }
}
