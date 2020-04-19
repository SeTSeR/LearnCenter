package com.setser.learningcenter.course;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.model.BaseEntity;
import com.setser.learningcenter.pupil.Pupil;
import com.setser.learningcenter.teacher.Teacher;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="course")
public class Course extends BaseEntity {

	private static final long serialVersionUID = 856971531241198546L;

	@Column
    private String description;

    @Column(name = "is_displayed")
    private boolean isDisplayed;

    @ManyToMany(mappedBy = "courses")
    @NotEmpty
    private final Set<Administrator> admins;

    @ManyToMany(mappedBy = "courses")
    private final Set<Pupil> pupils;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private final Set<Lesson> lessons;

    public Course() {
        admins = new HashSet<>();
        pupils = new HashSet<>();
        lessons = new HashSet<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAdmin(Administrator admin) {
        admins.add(admin);
    }

    public void addPupil(Pupil pupil) {
        pupils.add(pupil);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Administrator> getAdministrators() {
        return new ArrayList<>(admins);
    }

    public List<Pupil> getPupils() {
        return new ArrayList<>(pupils);
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public List<Teacher> getTeachers() {
        return lessons.stream().map(Lesson::getTeacher).collect(Collectors.toList());
    }

    public List<LocalDateTime> getDates() {
        LocalDateTime first = lessons
                .stream()
                .map(Lesson::getLessonTime)
                .min(LocalDateTime::compareTo)
                .orElseThrow();
        LocalDateTime second = lessons
                .stream()
                .map(Lesson::getLessonTime)
                .max(LocalDateTime::compareTo)
                .orElseThrow();
        return Stream.of(first, second).collect(Collectors.toList());
    }

    public boolean getIsDisplayed() {
        return isDisplayed;
    }

    public void setIsDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return getId().equals(course.getId()) &&
                isDisplayed == course.isDisplayed &&
                description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), description, isDisplayed);
    }
}
