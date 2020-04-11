package com.setser.learningcenter.teacher;

import com.setser.learningcenter.course.Lesson;
import com.setser.learningcenter.model.PublicUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="teacher")
public class Teacher extends PublicUser {
	private static final long serialVersionUID = -8909934074538874670L;

	@Column(name = "company_name")
    @NotNull
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private Set<Lesson> lessons;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
}
