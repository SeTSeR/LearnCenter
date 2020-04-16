package com.setser.learningcenter.administrator;

import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="administrator")
public class Administrator extends User {

	private static final long serialVersionUID = -4682986284015865023L;
	@ManyToMany
    @JoinTable(name = "admin_course",
                joinColumns = @JoinColumn(name="admin_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name="course_id", referencedColumnName = "id"))
    private Set<Course> courses;

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public boolean isPupil() {
        return false;
    }

    @Override
    public boolean getIsTeacher() {
        return false;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
}
