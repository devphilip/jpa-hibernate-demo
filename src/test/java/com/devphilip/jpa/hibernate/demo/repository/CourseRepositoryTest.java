package com.devphilip.jpa.hibernate.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.devphilip.jpa.hibernate.demo.JpaHibernateDemoApplication;
import com.devphilip.jpa.hibernate.demo.entity.Course;
import com.devphilip.jpa.hibernate.demo.entity.Review;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaHibernateDemoApplication.class)
public class CourseRepositoryTest {

	private static final Logger log = LoggerFactory.getLogger(CourseRepositoryTest.class);

	@Autowired
	CourseRepository repository;
	
	@Autowired
	EntityManager em;

	@Test
	public void findById_basic() {
		Course course = repository.findById(10001L);
		assertEquals("JPA Demo", course.getName());
	}
	
	@Test
	@Transactional
	public void findById_firstLevelCacheDemo() {
		Course course = repository.findById(10001L);
		log.info("First Course Retrieved {}", course);
		
		Course course1 = repository.findById(10001L);
		log.info("First Course Retrieved - again {}", course);
		
		assertEquals("JPA Demo", course.getName());
		assertEquals("JPA Demo", course1.getName());
	}

	@Test
	@DirtiesContext
	public void deleteById_basic() {
		repository.deleteById(10002L);
		assertNull(repository.findById(10002L));
	}

	@Test
	@DirtiesContext
	public void save_basic() {
		// Get a course
		Course course = repository.findById(10001L);
		assertEquals("JPA Demo", course.getName());

		// Update the name
		course.setName("JPA Demo - Updated");

		repository.save(course);

		// Check the value
		Course course1 = repository.findById(10001L);
		assertEquals("JPA Demo - Updated", course1.getName());
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager() {
		repository.playWithEntityManager();
	}
	
	@Test
	@Transactional
	public void retrieveReviewsForCourse() {
		Course course = repository.findById(10001L);
		log.info("{}", course.getReviews());
	}
	
	@Test
	@Transactional
	public void retrieveCourseForReview() {
		Review review = em.find(Review.class, 50001L);
		log.info("{}", review.getCourse());
	}

}
