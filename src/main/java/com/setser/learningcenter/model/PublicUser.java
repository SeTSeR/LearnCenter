package com.setser.learningcenter.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@MappedSuperclass
public abstract class PublicUser extends User {
	private static final long serialVersionUID = 7860242243240072206L;

	@Column(name="first_name")
    @NotNull
    @NotBlank(message = "Это поле не может быть пустым")
    private String firstName;

    @Column(name="last_name")
    @NotNull
    @NotBlank(message = "Это поле не может быть пустым")
    private String lastName;

    @Column
    private String patronymic;

    @Column
    @Size(max=150, message = "Длина текста не более 150 символов")
    private String bio;

    @Column(name="display_name")
    @NotNull
    private boolean displayName;

    @Column(name="display_mail")
    @NotNull
    private boolean displayMail;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean getDisplayName() {
        return displayName;
    }

    public void setDisplayName(boolean displayName) {
        this.displayName = displayName;
    }

    public boolean getDisplayMail() {
        return displayMail;
    }

    public void setDisplayMail(boolean displayMail) {
        this.displayMail = displayMail;
    }

    @Override
    public boolean getIsAdmin() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PublicUser that = (PublicUser) o;
        return displayName == that.displayName &&
                displayMail == that.displayMail &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(bio, that.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, patronymic, bio, displayName, displayMail);
    }
}
