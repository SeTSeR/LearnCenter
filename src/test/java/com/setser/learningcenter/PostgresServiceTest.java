package com.setser.learningcenter;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.course.Lesson;
import com.setser.learningcenter.db.DBException;
import com.setser.learningcenter.db.PostgresService;
import com.setser.learningcenter.model.BaseEntity;
import com.setser.learningcenter.model.User;
import com.setser.learningcenter.pupil.Pupil;
import com.setser.learningcenter.teacher.Teacher;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PostgresServiceTest {

    private static final PostgresService service = new PostgresService("com.setser.learningcenter-test");

    @Test
    void findCourses() throws DBException {
        List<Course> results = service.findCourses("", false);
        assertNotNull(results);
        assert (!results.isEmpty());
        assertEquals(2, results.size());
        assertEquals(1, results.get(0).getId());
        assertEquals(2, results.get(1).getId());
    }

    @Test
    void testFindCoursesWithFilter() throws DBException {
        List<Course> results = service.findCourses("Java", true);
        assertNotNull(results);
        assert (!results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getId());
    }

    @Test
    void testEmptyFindCoursesWithFilter() throws DBException {
        List<Course> results = service.findCourses("", true);
        assertNotNull(results);
        assert (!results.isEmpty());
        assertEquals(List.of(1L, 2L), results.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Test
    void testFindCoursesWithoutResults() throws DBException {
        List<Course> results = service.findCourses("hell", true);
        assertNotNull(results);
        assert (results.isEmpty());
    }

    @Test
    void testFindTeachers() throws DBException {
        List<Teacher> teachers = service.findTeachers("Kuznetsov");
        assertNotNull(teachers);
        assert (!teachers.isEmpty());
        assertEquals(1, teachers.size());
        assertEquals(1, teachers.get(0).getId());
    }

    @Test
    void testFindTeachersWithoutResults() throws DBException {
        List<Teacher> teachers = service.findTeachers("Lou");
        assertNotNull(teachers);
        assert (teachers.isEmpty());
    }

    @Test
    void testFindUserByMail() throws DBException {
        User user = service.findUserByMail("yegor256@gmail.com");
        assertNotNull(user);
        assertEquals(4, user.getId());
    }

    @Test
    void testFindUserByMailWithoutResults() throws DBException {
        User user = service.findUserByMail("bingo@example.com");
        assertNull(user);
    }

    @Test
    void testGetTimetableForPupil() throws DBException {
        Pupil pupil = (Pupil) service.findUserByMail("kate1984@bing.com");
        assertNotNull(pupil);
        assertEquals(4, pupil.getId());
        List<Lesson> timetable = service.getTimetableForPupil(pupil);
        assertNotNull(timetable);
        assertEquals(List.of(1L, 3L), timetable.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Test
    void testGetTimetableForTeacher() throws DBException {
        Teacher teacher = (Teacher) service.findUserByMail("yegor256@gmail.com");
        assertNotNull(teacher);
        assertEquals(teacher.getId(), 4);
        List<Lesson> timetable = service.getTimetableForTeacher(teacher);
        assertNotNull(timetable);
        assertEquals(List.of(4L, 3L), timetable.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Test
    void testGetTeachersForCourse() throws DBException {
        Course course = service.findCourses("Java", false).get(0);
        assertNotNull(course);
        List<Teacher> teachers = service.getTeachersForCourse(course);
        assertNotNull(teachers);
        assertEquals(List.of(2L, 4L), teachers.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }

    @Test
    void testCreateAndDeleteCourse() throws DBException {
        Course course = new Course();
        course.setDescription("Hell course");
        course.setDisplayed(false);
        Administrator admin = (Administrator) service.findUserByMail("nicolas@nmattia.com");
        assertNotNull(admin);
        course.addAdmin(admin);

        Lesson lesson = new Lesson();
        lesson.setDescription("First lesson");
        lesson.setCourse(course);
        Teacher teacher = (Teacher)service.findUserByMail("skuznetsov@example.com");
        assertNotNull(teacher);
        lesson.setTeacher(teacher);
        lesson.setLessonTime(LocalDateTime.parse("2020-03-17T16:20:00"));

        service.createCourse(course);
        service.createLesson(lesson);
        service.updateCourse(course);
        course.addLesson(lesson);
        List<Course> courses = service.findCourses("Hell", false);
        assertNotNull(courses);
        assert (!courses.isEmpty());
        assertEquals(courses.get(0), course);
        service.deleteCourse(course);
        courses = service.findCourses("Hell", false);
        assertNotNull(courses);
        assert (courses.isEmpty());
    }

    @Test
    void testRegisterAndDeletePupil() throws DBException {
        Pupil pupil = new Pupil();
        pupil.setBio("First pupil ever");
        pupil.setDisplayMail(true);
        pupil.setDisplayName(true);
        pupil.setFirstName("Kevin");
        pupil.setLastName("Beason");
        pupil.setMail("kevin.beason@gmail.com");
        pupil.setPassHash("acd7865f4e5e523740f7dc35f6375c6e30242c5d87b688c05c4a331303f794c1");

        service.registerPupil(pupil);
        Pupil p = (Pupil)service.findUserByMail("kevin.beason@gmail.com");
        assertNotNull(p);
        assertEquals(pupil, p);
        service.deletePupil(pupil);
        assertNull(service.findUserByMail("kevin.beason@gmail.com"));
    }

    @Test
    void testDoubleRegisterPupil() {
        Pupil pupil = new Pupil();
        pupil.setBio("Best pupil ever");
        pupil.setDisplayMail(true);
        pupil.setDisplayName(true);
        pupil.setFirstName("Kevin");
        pupil.setLastName("Beason");
        pupil.setMail("skuznetsov@example.com");
        pupil.setPassHash("acd7865f4e5e523740f7dc35f6375c6e30242c5d87b688c05c4a331303f794c1");

        Exception exception = assertThrows(DBException.class, () -> service.registerPupil(pupil));
        String expectedMessage = "User with mail skuznetsov@example.com is already registered";
        assert(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testRegisterAndDeleteTeacher() throws DBException {
        Teacher teacher = new Teacher();
        teacher.setCompanyName("Compiler explorer");
        teacher.setDisplayName(true);
        teacher.setDisplayMail(true);
        teacher.setFirstName("Matt");
        teacher.setLastName("Godbolt");
        teacher.setMail("matt@godbolt.org");
        teacher.setPassHash("7efacc4d42915f118da4ea5b06e3f51ae67bb2e4caece754b6718b9adc5b3289");

        service.registerTeacher(teacher);
        assertEquals(teacher, service.findUserByMail("matt@godbolt.org"));
        service.deleteTeacher(teacher);
        assertNull(service.findUserByMail("matt@godbolt.org"));
    }

    @Test
    void testDoubleRegisterTeacher() {
        Teacher teacher = new Teacher();
        teacher.setCompanyName("Compiler explorer");
        teacher.setDisplayName(true);
        teacher.setDisplayMail(true);
        teacher.setFirstName("Matt");
        teacher.setLastName("Godbolt");
        teacher.setMail("chrome@google.com");
        teacher.setPassHash("7efacc4d42915f118da4ea5b06e3f51ae67bb2e4caece754b6718b9adc5b3289");

        Exception exception = assertThrows(DBException.class, () -> service.registerTeacher(teacher));
        String expectedMessage = "User with mail chrome@google.com is already registered";
        assert(exception.getMessage().contains(expectedMessage));
    }
}