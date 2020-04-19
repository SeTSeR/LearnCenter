package com.setser.learningcenter.db;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.course.Lesson;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.model.User;
import com.setser.learningcenter.pupil.Pupil;
import com.setser.learningcenter.teacher.Teacher;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface DBService {
    List<Course> findCourses(String query, boolean filterShowed) throws DBException;

    Course getCourseById(Long id) throws DBException;

    List<Teacher> findTeachers(String query) throws DBException;

    Teacher getTeacherById(Long id) throws DBException;

    Pupil getPupilById(Long id) throws DBException;

    User findUserByMail(String mail) throws DBException;

    List<Lesson> getTimetableForPupil(Pupil pupil) throws DBException;

    List<Lesson> getTimetableForTeacher(Teacher teacher) throws DBException;

    List<Teacher> getTeachersForCourse(Course course) throws DBException;

    void createCourse(Course course) throws DBException;

    void createLesson(Lesson lesson) throws DBException;

    void registerTeacher(Teacher teacher) throws DBException;

    void registerPupil(Pupil pupil) throws DBException;

    void updateCourse(Course course) throws DBException;

    void updateLesson(Lesson lesson) throws DBException;

    void updatePupil(Pupil pupil) throws DBException;

    void updateTeacher(Teacher teacher) throws DBException;

    void deleteCourse(Course course) throws DBException;

    void deleteLesson(Lesson lesson) throws DBException;

    void deletePupil(Pupil pupil) throws DBException;

    void deleteTeacher(Teacher teacher) throws DBException;
}
