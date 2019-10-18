package com.inti.formation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inti.formation.SprintTestunitaireApplication;
import com.inti.formation.controllers.UserController;
import com.inti.formation.dao.UserDaoTest;
import com.inti.formation.entities.User;
import com.inti.formation.services.UserService;

@WebAppConfiguration // Classe qui va adresser des requetes web
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SprintTestunitaireApplication.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Autowired
	// WebApplicationContext : objet qui appartient à Spring framework: nous permet
	// d'exécuter les requetes web
	WebApplicationContext webApplicationContext;

	@Mock
	private UserService userServiceToMock;

	/**
	 * Used to mock the Web Context
	 */
	protected MockMvc mvc; // Rendre ce webContext comme un mock (= faux) pour une simulation

	/**
	 * Used for the web service adressing. You need to initiate it in the subclasses
	 * contructors
	 */
	protected String uri;

	@Before
	public void setUp() {
		// Construit une fausse image du webApplicationContext, .build = pour créer
		// (appartient à Lombok)
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
	}

	// Constructeur: initialise le this.uri à "/user"
	@Autowired
	private UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

	public UserControllerTest() {
		super();
		this.uri = "/user";
	}

	@Test
	public void getAllEntityList() {
		// Contiendra la réponse de notre requete http
		MvcResult mvcResult;

		try {
			LOGGER.info("-------------------------- Testing getAllEntity Method -------------------------");
			LOGGER.info("-------------------------- Contructing Utilisateur -------------------------");
			LOGGER.info("-------------------------- Saving Utilisateur -------------------------");
			userService.addUser(new User(2, "dalii")); // Fait appel à la méthode add

			LOGGER.info("-------------------------- Mocking Contexte Webservice -------------------------");
			/*
			 * perform = pour exécuter la requete. mvcResult reçoit la fausse image mvc,
			 * accept(MediaType.APPLICATION_JSON_VALUE) = Accepter un type correspondant à
			 * json et le retourne
			 */
			mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/all").accept(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();

			LOGGER.info("-------------------------- Getting HTTP Status-------------------------");
			// Retourne le statut de la requete
			int status = mvcResult.getResponse().getStatus();

			LOGGER.info("-------------------------- Verifying HTTP Status -------------------------");
			assertEquals(200, status);

			LOGGER.info("-------------------------- Getting HTTP Status -------------------------");
			// On récupère len body de la reponse dans une chaine de caractère appelait
			// content
			String content = mvcResult.getResponse().getContentAsString();

			LOGGER.info("-------------------------- Deserializing JSON Response -------------------------");
			// Convertit en un tableau de users, indique le type (on veut un tableau de
			// users)
			User[] userList = this.mapFromJson(content, User[].class);
			// Vérifie si la taille est bien supérieure à 0
			assertTrue(userList.length > 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void createEntity() {
		// On veut tester si le webService utilise la méthode save de UserController
		LOGGER.info("--------------------------Testing createEntity Method -------------------------");
		LOGGER.info("--------------------------Constructing Utilisateur -------------------------");
		User user = new User(50, "sala7");

		MvcResult mvcResult;
		try {
			LOGGER.info("--------------------------Serializing Utilisateur Object -------------------------");
			String inputJson = this.mapToJson(user); // Créer des json (pour qu'il soit capable de le lire) à partir de
														// la classe: cf méthodes : mapToJson, mapFromJson

			LOGGER.info(
					"--------------------------Mocking Contexte Webservice and invocking the webservice -------------------------");
			// Ici il exécute la requete POST
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + "/adduser")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			LOGGER.info("--------------------------Getting HTTP Status-------------------------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("--------------------------Verifying HTTP Status -------------------------");
			assertEquals(200, status);
			LOGGER.info("--------------------------Searching for Utilisateur -------------------------");
			User userFound = userService.getUserById(new Long(50));
			LOGGER.info("--------------------------Verifying Utilisateur -------------------------");
			// Vérifie que c'est le même utilisateur
			assertEquals(userFound.getUserName(), user.getUserName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Pour UserService = @Mock
	// pour UserController = @Inject mock
	@Test
	public void should_use_add_when_addUser_is_called() {
		LOGGER.info(
				"--------------- Executing should_use_add_when_addUser_is_called test Of UserControllerTest ---------------");
		User user = new User();
		userController.addNewUser(user);
		verify(userServiceToMock).addUser(user);
	}

	//Methode pour vérifier le status http
	@Test
	public void verify_status() {

		LOGGER.info("-------------------------- Testing verify_status method -------------------------");
		LOGGER.info("-------------------------- Constructing Utilisateur -------------------------");
		User user1 = new User(50, "sala7");

		MvcResult mvcResult;
		
		try {
			LOGGER.info("--------------------------Serializing Utilisateur Object -------------------------");
			String inputJson = this.mapToJson(user1); 
			LOGGER.info("-------------------------- Mocking Contexte Webservice and invocking the webservice -------------------------");
			
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + "/adduser")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			LOGGER.info("--------------------------Getting HTTP Status-------------------------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("--------------------------Verifying HTTP Status -------------------------");
			assertEquals(200, status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateEntity() {
		try {
			LOGGER.info("--------------------------Testing updateEntity Method -------------------------");
			LOGGER.info("--------------------------Constructing Utilisateur -------------------------");
			User oldUser = new User(2, "Lemon");

			LOGGER.info("-------------------------- Saving Utilisateur -------------------------");
			userService.addUser(oldUser);

			LOGGER.info("-------------------------- Modifying Utilisateur -------------------------");
			User newUser = new User(2, "Lemonade");

			LOGGER.info("-------------------------- Serializing Utilisateur Object -------------------------");
			String inputJson = this.mapToJson(newUser);

			LOGGER.info(
					"--------------------------Mocking Contexte Webservice and invocking the webservice -------------------------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/2")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

			LOGGER.info("--------------------------Getting HTTP Status-------------------------");
			int status = mvcResult.getResponse().getStatus();

			LOGGER.info("--------------------------Verifying HTTP Status -------------------------");
			assertEquals(200, status);

			LOGGER.info("--------------------------Searching for Utilisateur -------------------------");
			User userFound = userService.getUserById(new Long(2));

			LOGGER.info("--------------------------Verifying Utilisateur -------------------------");
			assertEquals(userFound.getUserName(), newUser.getUserName());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void deleteEntity() {
		LOGGER.info("-------------------------- Testing deleteEntity Method -------------------------");

		try {
			LOGGER.info("-------------------------- Constructing Utilisateur -------------------------");
			LOGGER.info("-------------------------- Saving Utilisateur -------------------------");
			userService.addUser(new User(2, "Lemon"));

			LOGGER.info(
					"--------------------------Mocking Contexte Webservice and invocking the webservice -------------------------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + "/2")).andReturn();

			LOGGER.info("-------------------------- Getting HTTP Status-------------------------");
			int status = mvcResult.getResponse().getStatus();

			LOGGER.info("-------------------------- Verifying HTTP Status -------------------------");
			assertEquals(200, status);

			LOGGER.info("-------------------------- Searching for Utilisateur -------------------------");
			User userFound = userService.getUserById(new Long(2));

			LOGGER.info("-------------------------- Verifying Utilisateur -------------------------");
			assertEquals(userFound, null);
			// ou assertEquals(none);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Deux méthodes

	// Pour que notre objet java soit transformé en objet json (string)
	/**
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected final String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}


	// Objet Json vers objet java
	/**
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected final <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}


}
