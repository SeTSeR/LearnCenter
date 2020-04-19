package com.setser.learningcenter.teacher;

import com.setser.learningcenter.course.Lesson;
import com.setser.learningcenter.model.PublicUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="teacher")
public class Teacher extends PublicUser {
	private static final long serialVersionUID = -8909934074538874670L;

	@Column(name = "company_name")
    @NotNull
    @Size(max=50, message = "Длина названия компании от 10 до 50 символов")
    private String companyName;

    @OneToMany(mappedBy = "teacher")
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

    @Override
    public boolean getIsPupil() {
        return false;
    }

    @Override
    public boolean getIsTeacher() {
        return true;
    }
}
