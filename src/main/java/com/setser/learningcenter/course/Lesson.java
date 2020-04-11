package com.setser.learningcenter.course;

import com.setser.learningcenter.model.BaseEntity;
import com.setser.learningcenter.teacher.Teacher;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="lesson")
public class Lesson extends BaseEntity {
	private static final long serialVersionUID = 4436363744891200152L;

	@Column
    private String description;

    @Column(name="lesson_time")
    @NotNull
    private LocalDateTime lessonTime;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLessonTime() { return lessonTime; }

    public void setLessonTime(LocalDateTime lessonTime) {
        this.lessonTime = lessonTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return description.equals(lesson.description) &&
                lessonTime.equals(lesson.lessonTime) &&
                course.equals(lesson.course) &&
                teacher.equals(lesson.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, lessonTime, course, teacher);
    }
}
