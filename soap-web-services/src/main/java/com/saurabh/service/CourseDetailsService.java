package com.saurabh.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saurabh.entity.Course;
import com.saurabh.exception.CourseNotFoundException;
import com.saurabh.repository.CourseRepository;

@Component
public class CourseDetailsService {
	@Autowired
	private CourseRepository courseRepository;

	public enum Status {
		SUCCESS, FAILURE;
	}

	private static List<Course> courses = new ArrayList<>();

	static {
		Course course1 = new Course(1, "Spring", "10 Steps");
		courses.add(course1);

		Course course2 = new Course(2, "Spring MVC", "10 Examples");
		courses.add(course2);

		Course course3 = new Course(3, "Spring Boot", "6K Students");
		courses.add(course3);

		Course course4 = new Course(4, "Maven", "Most popular maven course on internet!");
		courses.add(course4);
	}

	// course - 1
	public Course findById(int id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new CourseNotFoundException("Course with the given id is not available"));
		return course;
	}

	// courses
	public List<Course> findAll() {
		return (List<Course>) courseRepository.findAll();
	}

	public Status deleteById(int id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new CourseNotFoundException("Course with the given id is not available"));
		courseRepository.deleteById(id);
		return Status.SUCCESS;
	}

	// updating course & new course
	public Status addNewCourse(Course course) {
		courseRepository.save(course);
		return Status.SUCCESS;
	}
}
