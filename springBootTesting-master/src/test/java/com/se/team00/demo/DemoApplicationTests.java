package com.se.team00.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.OptionalInt;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.se.team00.demo.Student;
import com.se.team00.demo.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Validator validator;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testStudentFirstNameCannotBeNull() {
        Student s = new Student();
        s.setFirstName(null);
        s.setLastName("Abcd");
        s.setStudentId("B59000001");

        try {
            entityManager.persist(s);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }

    @Test
    public void testStudentLastNameCannotBeNull() {
        Student s = new Student();
        s.setFirstName("Abc");
        s.setLastName(null);
        s.setStudentId("B59000000");

        try {
            entityManager.persist(s);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void testIdMustBeUnique() {
        Student s1 = new Student();
        s1.setFirstName("Abcd");
        s1.setLastName("Abcd");
        s1.setStudentId("B59000000");
        entityManager.persist(s1);
        entityManager.flush();

        Student s2 = new Student();
        s2.setFirstName("Defg");
        s2.setLastName("Defg");
        s2.setStudentId("B59000000");

        entityManager.persist(s2);
        entityManager.flush();

        fail("Should not pass to this line");
    }

    @Test
    public void testFistCharNotABC() {
        Student s1 = new Student();
        s1.setFirstName("Abcd");
        s1.setLastName("Abcd");
        s1.setStudentId("D59000000");
        try {
            entityManager.persist(s1);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }

    @Test
    public void testLengthNotEquals9() {
        Student s1 = new Student();
        s1.setFirstName("Abcd");
        s1.setLastName("Abcd");
        s1.setStudentId("A59000");
        try {
            entityManager.persist(s1);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }

    @Test
    public void testStudentIdNotDigit() {
        Student s1 = new Student();
        s1.setFirstName("Abcd");
        s1.setLastName("Abcd");
        s1.setStudentId("A123045BA");
        try {
            entityManager.persist(s1);
            entityManager.flush();

            fail("Should not pass to this line");
        } catch (javax.validation.ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations.isEmpty(), false);
            assertEquals(violations.size(), 1);
        }
    }
}


