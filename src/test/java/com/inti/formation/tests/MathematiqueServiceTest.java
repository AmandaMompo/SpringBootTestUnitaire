package com.inti.formation.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inti.formation.entities.MathematiqueService;

@RunWith(SpringRunner.class)
@SpringBootTest //Test de l'application spring boot
public class MathematiqueServiceTest {
	
	@Test //Cette méthode: additionTest() est un test pour tester la méthode addition
	public void additionTest() {
		MathematiqueService mathServ = new MathematiqueService();
		long a = 5;
		long b = 6; 
		long result = mathServ.addition(a, b);
		assertEquals(result, 11); //asertEquaks: dit le résultat attendu, si égal à 11: ok, si différent: exception, le test à échouer
	}

}
