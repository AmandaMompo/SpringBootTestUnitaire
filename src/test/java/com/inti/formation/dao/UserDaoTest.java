package com.inti.formation.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inti.formation.SprintTestunitaireApplication;
import com.inti.formation.entities.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SprintTestunitaireApplication.class)
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

	@Test
	public void givenEntityRepository_whenSaving() {
		LOGGER.info("-----------------Testing givenEntityRepository_whenSaving Method ---------------------");
		System.out.println(userDao);
		User addedUser = userDao.save(new User(10, "dalii"));
		LOGGER.info("----------------- Utilisateur saved ---------------------");
		LOGGER.info("----------------- Searching for Utilisateur---------------------");
		User foundUser = userDao.findById(addedUser.getId());
		LOGGER.info("----------------- Utilisateur found ---------------------");
		LOGGER.info("----------------- Verifying Utilisateur ---------------------");
		assertNotNull(foundUser);
		assertEquals(addedUser.getUserName(), foundUser.getUserName());
		LOGGER.info("----------------- Utilisateur veridied ---------------------");
	}


}
