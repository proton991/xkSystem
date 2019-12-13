package com.fudan.xk.repository;

import com.fudan.xk.model.Classroom;
import com.fudan.xk.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fudan.xk.model.Course;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

	@Modifying
	@Query("update Course c set c.selectedQuantity = c.selectedQuantity + 1 where c.courseId = :courseId and c.stockQuantity - c.selectedQuantity > 0")
	int addSelectByCourseId(@Param(value = "courseId") String courseId);

	Course findCourseByCourseId(String courseId);

	List<Course> findCoursesByTeachers(Teacher teacher);

	List<Course> findCoursesByCourseNameContaining(String name);

	@Modifying
	@Query("update Course c set c.selectedQuantity = c.selectedQuantity - 1 where c.courseId = :courseId and c.selectedQuantity > 0")
	int dropCourseByCourseId(@Param(value = "courseId") String courseId);

}
