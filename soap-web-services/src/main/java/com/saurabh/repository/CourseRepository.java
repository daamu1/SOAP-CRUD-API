package com.saurabh.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.saurabh.entity.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {

}
