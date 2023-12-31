package com.saurabh.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.saurabh.entity.Course;
import com.saurabh.courses.AddNewCourseDetailsRequest;
import com.saurabh.courses.AddNewCourseDetailsResponse;
import com.saurabh.courses.CourseDataDetails;
import com.saurabh.courses.CourseDetails;
import com.saurabh.courses.DeleteCourseDetailsRequest;
import com.saurabh.courses.DeleteCourseDetailsResponse;
import com.saurabh.courses.GetAllCourseDetailsRequest;
import com.saurabh.courses.GetAllCourseDetailsResponse;
import com.saurabh.courses.GetCourseDetailsRequest;
import com.saurabh.courses.GetCourseDetailsResponse;
import com.saurabh.exception.CourseNotFoundException;
import com.saurabh.service.CourseDetailsService;
import com.saurabh.service.CourseDetailsService.Status;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	// http://in28minutes.com/courses
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = "http://saurabh.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Course course = service.findById(request.getId());

		if (course == null)
			throw new CourseNotFoundException("Invalid Course Id " + request.getId());

		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();

		courseDetails.setId(course.getId());

		courseDetails.setName(course.getName());

		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

	@PayloadRoot(namespace = "http://saurabh.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {

		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);
	}

	@PayloadRoot(namespace = "http://saurabh.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

		Status status = service.deleteById(request.getId());

		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));

		return response;
	}
	@PayloadRoot(namespace = "http://saurabh.com/courses", localPart = "AddNewCourseDetailsRequest")
	@ResponsePayload
	public AddNewCourseDetailsResponse addCourseDetailsRequest(@RequestPayload AddNewCourseDetailsRequest request) {
	    CourseDataDetails courseDetails = request.getCourseDataDetails();
	    Course newCourse = new Course();
	    newCourse.setName(courseDetails.getName());
	    newCourse.setDescription(courseDetails.getDescription());

	    Status status = service.addNewCourse(newCourse);
	    
	    AddNewCourseDetailsResponse response = new AddNewCourseDetailsResponse();
	    response.setStatus(mapStatus(status));
	    return response;
	}


	private com.saurabh.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE)
			return com.saurabh.courses.Status.FAILURE;
		return com.saurabh.courses.Status.SUCCESS;
	}
}
