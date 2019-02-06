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

@DataJpaTest
public class DemoApplicationTests {


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
        propertyPolicy.setPropertyName("abcdef");
        propertyPolicy.setDetail("Abcdefghijklmnopqrstuvwxyz");
        propertyPolicy.setCostPolicy(1500);

        entityManager.flush();
            System.out.println("***************test All*************");

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
            System.out.println("********************************test Name***********************");
            System.out.println(e);
        }
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testPropertyNameBeUnique() {
        PropertyPolicy p1 = new PropertyPolicy();
        p1.setPropertyName("Abcdef");
        p1.setDetail("abcdefghijklmnopqrstuvwxyz");
        p1.setCostPolicy(1500);


        entityManager.persist(p1);
        entityManager.flush();

        PropertyPolicy p2 = new PropertyPolicy();
        p2.setPropertyName("Abcdef");
        p2.setDetail("abcdefghijklmnopqrstuvwxyz");
        p2.setCostPolicy(1500);
try{
    entityManager.persist(p2);
    entityManager.flush();
    fail("Should not pass to this line");
}catch (javax.validation.ConstraintViolationException e) {
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    assertEquals(violations.isEmpty(), false);
    assertEquals(violations.size(), 1);
    System.out.println("********************************test unique********************");
    System.out.println(e);

}

    }

    @Test
    public void testproprtyNameMoreThanMax() {
        PropertyPolicy propertyPolicy = new PropertyPolicy();
        propertyPolicy.setPropertyName("abcdefghijklmnopqrstuvwxyzabcdefg");
        propertyPolicy.setDetail("abcdefghijklmnopqrstuvwxyz");
        propertyPolicy.setCostPolicy(1500);

        try {
            entityManager.persist(propertyPolicy);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
            System.out.println("********************************test Property name more than Max********************");
            System.out.println(e);
        }
    }
    @Test
    public void testproprtyNameFistNotChar() {
        PropertyPolicy propertyPolicy = new PropertyPolicy();
        propertyPolicy.setPropertyName("8abcd");
        propertyPolicy.setDetail("abcdefghijklmnopqrstuvwxyz");
        propertyPolicy.setCostPolicy(1500);

        try {
            entityManager.persist(propertyPolicy);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
            System.out.println("********************************test Property name first not char********************");
            System.out.println(e);
        }
    }

    @Test
    public void testDetailLessThanMin() {
        PropertyPolicy propertyPolicy = new PropertyPolicy();
        propertyPolicy.setPropertyName("abcdef");
        propertyPolicy.setDetail("Abcde");
        propertyPolicy.setCostPolicy(1500);

        try {
            entityManager.persist(propertyPolicy);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
            System.out.println("********************************test detail min********************");
            System.out.println(e);
        }
    }

    @Test
    public void testCostPolicyLessThanValue() {
        PropertyPolicy propertyPolicy = new PropertyPolicy();
        propertyPolicy.setPropertyName("abcdef");
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
            System.out.println("********************************test cost************************");
            System.out.println(e);
        }
    }


}

