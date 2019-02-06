package com.sut.se61.g17;


import com.sut.se61.g17.entity.PropertyPolicy;
import com.sut.se61.g17.repository.PropertyPolicyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private PropertyPolicyRepository propertyPolicyRepository;

	@Autowired
	private TestEntityManager entityManager;


	private Validator validator;


	@Before
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testProperty() {
		PropertyPolicy propertyPolicy = new PropertyPolicy();
		propertyPolicy.setPropertyName("abcd");
		propertyPolicy.setDetail("Abcdefghijklmnopqrstuvwxyz");
		propertyPolicy.setCostPolicy(1500);


		try {
			entityManager.persist(propertyPolicy);
			entityManager.flush();

			fail("Should not pass to this line");
		} catch (javax.validation.ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			assertEquals(violations.isEmpty(), false);
			assertEquals(violations.size(), 1);
			System.out.println(e);
		}
	}
	@Test
	public void testPropertyNameCannotBeNull() {
		PropertyPolicy propertyPolicy = new PropertyPolicy();
		propertyPolicy.setPropertyName(null);
		propertyPolicy.setDetail("Abcdefghijklmnopqrstuvwxyz");
		propertyPolicy.setCostPolicy(1500);


		try {
			entityManager.persist(propertyPolicy);
			entityManager.flush();

			fail("Should not pass to this line");
		} catch (javax.validation.ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			assertEquals(violations.isEmpty(), false);
			assertEquals(violations.size(), 1);
			System.out.println(e);
		}
	}

	@Test(expected = javax.persistence.PersistenceException.class)
	public void testPropertyNameBeUnique() {
		PropertyPolicy p1 = new PropertyPolicy();
		p1.setPropertyName("Abcd");
		p1.setDetail("abcdefghijklmnopqrstuvwxyz");
		p1.setCostPolicy(1500);
		entityManager.persist(p1);
		entityManager.flush();

		PropertyPolicy p2 = new PropertyPolicy();
		p2.setPropertyName("Abcd");
		p2.setDetail("abcdefghijklmnopqrstuvwxyz");
		p2.setCostPolicy(1500);
		entityManager.persist(p2);
		entityManager.flush();

		fail("Should not pass to this line");
	}

	@Test
	public void testDetailLessThanMin() {
		PropertyPolicy propertyPolicy = new PropertyPolicy();
		propertyPolicy.setPropertyName(null);
		propertyPolicy.setDetail("Abcd");
		propertyPolicy.setCostPolicy(1500);

		try {
			entityManager.persist(propertyPolicy);
			entityManager.flush();

			fail("Should not pass to this line");
		} catch (javax.validation.ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			assertEquals(violations.isEmpty(), false);
			assertEquals(violations.size(), 1);
			System.out.println(e);
		}
	}

	@Test
	public void testCostPolicyLessThanValue() {
		PropertyPolicy propertyPolicy = new PropertyPolicy();
		propertyPolicy.setPropertyName("ABCD");
		propertyPolicy.setDetail("Abcdefghijklmnopqrstuvwxyz");
		propertyPolicy.setCostPolicy(50);

		try {
			entityManager.persist(propertyPolicy);
			entityManager.flush();

			fail("Should not pass to this line");
		} catch (javax.validation.ConstraintViolationException e) {
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			assertEquals(violations.isEmpty(), false);
			assertEquals(violations.size(), 1);
			System.out.println(e);
		}
	}


}

