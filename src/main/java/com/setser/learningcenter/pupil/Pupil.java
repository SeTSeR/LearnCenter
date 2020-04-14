package com.setser.learningcenter.pupil;

import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.model.PublicUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pupil")
public class Pupil extends PublicUser {
	private static final long serialVersionUID = 4645530113376455219L;
	@ManyToMany
    @JoinTable(name = "pupil_course",
                joinColumns = @JoinColumn(name = "pupil_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> courses;

    public Set<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public boolean isPupil() {
        return true;
    }

    @Override
    public boolean isTeacher() {
        return false;
    }
}
