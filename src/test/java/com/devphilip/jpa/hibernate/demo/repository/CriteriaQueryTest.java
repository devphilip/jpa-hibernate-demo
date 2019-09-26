package com.devphilip.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devphilip.jpa.hibernate.demo.JpaHibernateDemoApplication;
import com.devphilip.jpa.hibernate.demo.entity.Course;
import com.devphilip.jpa.hibernate.demo.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class CriteriaQueryTest {

	private static final Logger log = LoggerFactory.getLogger(CriteriaQueryTest.class);

	@Autowired
	EntityManager em;

	@Test
	public void all_courses() {
		// "Select c From Course c"
		
		// 1. Use Criteria Builder to create a Criteria Query returning the 
			//expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder(); 
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define Predicates etc using Criteria Builder
		// 4. Add Predicates etc to the Criteria Query
		
		// 5. Build the TypedQuery using the entity manager
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		log.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void all_courses_having_100Steps() {
		// "Select c From Course c where name like '%100 Steps'"
		
		// 1. Use Criteria Builder to create a Criteria Query returning the 
			//expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder(); 
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define Predicates etc using Criteria Builder
		Predicate like100Steps = cb.like(courseRoot.get("name"), "%100 Steps");
		
		// 4. Add Predicates etc to the Criteria Query
		cq.where(like100Steps);
		
		// 5. Build the TypedQuery using the entity manager
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		log.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void all_courses_without_students() {
		// "Select c From Course c where c.students is empty"
		
		// 1. Use Criteria Builder to create a Criteria Query returning the 
			//expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder(); 
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define Predicates etc using Criteria Builder
		Predicate studentIsEmpty = cb.isEmpty(courseRoot.get("students"));
		
		// 4. Add Predicates etc to the Criteria Query
		cq.where(studentIsEmpty);
		
		// 5. Build the TypedQuery using the entity manager
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		log.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void join() {
		// "Select c, s from Course c JOIN c.students s"
		
		// 1. Use Criteria Builder to create a Criteria Query returning the 
			//expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder(); 
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define Predicates etc using Criteria Builder
		Join<Object, Object> join = courseRoot.join("students");
		
		// 4. Add Predicates etc to the Criteria Query
		
		
		// 5. Build the TypedQuery using the entity manager
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		log.info("Typed Query -> {}", resultList);
	}
	
	@Test
	public void left_join() {
		// "Select c, s from Course c JOIN c.students s"
		
		// 1. Use Criteria Builder to create a Criteria Query returning the 
			//expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder(); 
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
		Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define Predicates etc using Criteria Builder
		Join<Object, Object> join = courseRoot.join("students", JoinType.LEFT);
		
		// 4. Add Predicates etc to the Criteria Query
		
		
		// 5. Build the TypedQuery using the entity manager
		TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
		List<Course> resultList = query.getResultList();
		log.info("Typed Query -> {}", resultList);
	}

}





