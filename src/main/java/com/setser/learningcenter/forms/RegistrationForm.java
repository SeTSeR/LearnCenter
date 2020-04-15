package com.setser.learningcenter.forms;

import com.setser.learningcenter.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm {
    @Email(message = "Предоставлена некорректная почта")
    @NotBlank(message = "Не указана почта")
    @Login(message = "Почта уже зарегистрирована")
    private String username;

    @Size(min=8, max=50, message = "Длина пароля должна быть от 8 до 50 символов")
    @NotBlank(message = "Не указан пароль")
    private String password;

    @NotBlank(message = "Это поле не может быть пустым")
    private String firstName;

    @NotBlank(message = "Поле не может быть пустым")
    private String lastName;

    @NotNull
    private String patronymic;

    @Size(max=150, message = "Длина текста не более 150 символов")
    private String bio;

    private boolean isTeacher;

    @Size(max=50, message = "Длина названия компании от 10 до 50 символов")
    private String companyName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "RegistrationForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", isTeacher=" + isTeacher +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
