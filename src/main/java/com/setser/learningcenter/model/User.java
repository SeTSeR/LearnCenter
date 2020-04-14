package com.setser.learningcenter.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@MappedSuperclass
public abstract class User extends BaseEntity {
	private static final long serialVersionUID = -7533057377651533690L;

	@Column
    @NotNull
    private String mail;

    @Column(name="pass_hash")
    @NotNull
    private String passHash;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public abstract boolean isAdmin();

    public abstract boolean isPupil();

    public abstract boolean isTeacher();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return mail.equals(user.mail) &&
                passHash.equals(user.passHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mail, passHash);
    }
}
