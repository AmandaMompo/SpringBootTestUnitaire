package com.inti.formation.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inti.formation.SprintTestunitaireApplication;
import com.inti.formation.dao.UserDao;
import com.inti.formation.dao.UserDaoTest;
import com.inti.formation.entities.User;
import com.inti.formation.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SprintTestunitaireApplication.class)
public class UserServiceTest {
	
	//@Autowired
	//@Mock
	@InjectMocks
	private UserService userServiceToMock;  //Création du faux objet
	
	@Autowired
	private UserService userService;
	
	
	//@Autowired
	@Mock
	private UserDao userDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Before //Méthode exécutée avant chaque test
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void givenUsers_getHalfOfNumber() {
		LOGGER.info("-------------------------- Testing givenUsers_getHalfOfNumber Method ----------------------");
		LOGGER.info("-------------------------- Constructing Utilisateurs ----------------------");
//		userServiceToMock = Mockito.mock(UserService.class);
		LOGGER.info("-------------------------- Mocking getAll() method of IUtilisateurService ----------------------");
		//Quand l'objet userServiceToMock fait appel à la méthode: il retourne le tableau
		Mockito.when(userServiceToMock.getAllUsers()).thenReturn(java.util.Arrays.asList(new User(1, "dalii"), new User(2, "dalii"), new User(3, "dalii"), new User(18, "dalii")));
		LOGGER.info("-------------------------- Metho d Mocked ----------------------");
		LOGGER.info("-------------------------- Verifying results ----------------------");
		assertEquals(2, userService.getUserNbrHalf(userServiceToMock.getAllUsers())); //Retourne toujours le tableau
	}

	@Test
	public void should_store_when_save_is_called() {
		LOGGER.info("-------------------------- Executing should_store_when_save_" + "is_called test of UserServiceImplTest ----------------------");
		User myUser = new User();
		userServiceToMock.addUser(myUser);
		Mockito.verify(userDao).save(myUser);
	}

	
}
