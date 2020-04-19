package com.setser.learningcenter.db;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.administrator.AdministratorDAO;
import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.course.CourseDAO;
import com.setser.learningcenter.course.Lesson;
import com.setser.learningcenter.course.LessonDAO;
import com.setser.learningcenter.model.User;
import com.setser.learningcenter.pupil.Pupil;
import com.setser.learningcenter.pupil.PupilDAO;
import com.setser.learningcenter.teacher.Teacher;
import com.setser.learningcenter.teacher.TeacherDAO;
import org.hibernate.HibernateException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

@Component
public class PostgresService implements DBService {

    private final EntityManagerFactory entityManagerFactory;

    public PostgresService() {
        this("com.setser.learningcenter");
    }

    public PostgresService(String persistenceUnitName) {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        } catch (Throwable t) {
            System.out.println("SessionFactory creation failed: " + t);
            throw new ExceptionInInitializerError(t);
        }
    }

    @Override
    public Administrator getAdministratorById(Long id) throws DBException {
        try (AdministratorDAO dao = new AdministratorDAO(entityManagerFactory.createEntityManager())) {
            return dao.getById(id);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Course> findCourses(String query, boolean filterShowed) throws DBException {
        try (CourseDAO dao = new CourseDAO(entityManagerFactory.createEntityManager())) {
            return dao.findCourses(query, filterShowed);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Course getCourseById(Long id) throws DBException {
        try (CourseDAO dao = new CourseDAO(entityManagerFactory.createEntityManager())) {
            return dao.getById(id);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Teacher> findTeachers(String query) throws DBException {
        try (TeacherDAO dao = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            return dao.findTeachers(query);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Teacher getTeacherById(Long id) throws DBException {
        try (TeacherDAO dao = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            return dao.getById(id);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Pupil getPupilById(Long id) throws DBException {
        try(PupilDAO dao = new PupilDAO(entityManagerFactory.createEntityManager())) {
            return dao.getById(id);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public User findUserByMail(String mail) throws DBException {
        try (AdministratorDAO adminDAO = new AdministratorDAO(entityManagerFactory.createEntityManager());
             PupilDAO pupilDAO = new PupilDAO(entityManagerFactory.createEntityManager());
             TeacherDAO teacherDAO = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            for (var dao : List.of(adminDAO, pupilDAO, teacherDAO)) {
                try {
                    return dao.findUserByMail(mail);
                } catch (NoResultException ignored) {
                }
            }
            return null;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Lesson> getTimetableForPupil(Pupil pupil) throws DBException {
        try (PupilDAO dao = new PupilDAO(entityManagerFactory.createEntityManager())) {
            return dao.getTimetable(pupil);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Lesson> getTimetableForTeacher(Teacher teacher) throws DBException {
        try (TeacherDAO dao = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            return dao.getTimetable(teacher);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Teacher> getTeachersForCourse(Course course) throws DBException {
        try (CourseDAO dao = new CourseDAO(entityManagerFactory.createEntityManager())) {
            return dao.getTeachers(course);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createLesson(Lesson lesson) throws DBException {
        try (LessonDAO dao = new LessonDAO(entityManagerFactory.createEntityManager())) {
            dao.save(lesson);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createCourse(Course course) throws DBException {
        try (CourseDAO dao = new CourseDAO(entityManagerFactory.createEntityManager())) {
            dao.save(course);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void registerTeacher(@NotNull Teacher teacher) throws DBException {
        User user = findUserByMail(teacher.getMail());
        if (user != null) {
            throw new DBException("User with mail " + teacher.getMail() + " is already registered");
        }
        try (TeacherDAO dao = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            dao.save(teacher);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void registerPupil(@NotNull Pupil pupil) throws DBException {
        User user = findUserByMail(pupil.getMail());
        if (user != null) {
            throw new DBException("User with mail " + pupil.getMail() + " is already registered");
        }
        try (PupilDAO dao = new PupilDAO(entityManagerFactory.createEntityManager())) {
            dao.save(pupil);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateAdministrator(Administrator administrator) throws DBException {
        try (AdministratorDAO dao = new AdministratorDAO(entityManagerFactory.createEntityManager())) {
            dao.update(administrator);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateCourse(Course course) throws DBException {
        try (CourseDAO dao = new CourseDAO(entityManagerFactory.createEntityManager())) {
            dao.update(course);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateLesson(Lesson lesson) throws DBException {
        try (LessonDAO dao = new LessonDAO(entityManagerFactory.createEntityManager())) {
            dao.update(lesson);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updatePupil(Pupil pupil) throws DBException {
        try (PupilDAO dao = new PupilDAO(entityManagerFactory.createEntityManager())) {
            dao.update(pupil);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) throws DBException {
        try (TeacherDAO dao = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            dao.update(teacher);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteCourse(Course course) throws DBException {
        try (CourseDAO dao = new CourseDAO(entityManagerFactory.createEntityManager())) {
            dao.delete(course);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteLesson(Lesson lesson) throws DBException {
        try (LessonDAO dao = new LessonDAO(entityManagerFactory.createEntityManager())) {
            dao.delete(lesson);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deletePupil(Pupil pupil) throws DBException {
        try (PupilDAO dao = new PupilDAO(entityManagerFactory.createEntityManager())) {
            dao.delete(pupil);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteTeacher(Teacher teacher) throws DBException {
        try (TeacherDAO dao = new TeacherDAO(entityManagerFactory.createEntityManager())) {
            dao.delete(teacher);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }
}
