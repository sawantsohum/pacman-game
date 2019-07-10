package org.springframework.samples.petclinic.owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "profile")
public class Profile {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Highscore")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer highscore;

    @Column(name = "display_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String displayName;

    @Column(name = "avatar")
    @NotFound(action = NotFoundAction.IGNORE)
    private String avatar;

    @Column(name = "username")
    @NotFound(action = NotFoundAction.IGNORE)
    private String username;
    

    public Integer getHighscore() {
        return highscore;
    }

    public void setId(Integer highscore) {
        this.highscore = highscore;
    }

    public boolean isNew() {
        return this.highscore == null;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String firstName) {
        this.displayName = firstName;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setEmail(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("highscore", this.getHighscore())
                .append("new", this.isNew())
                .append("displayName", this.getDisplayName())
                .append("email", this.getAvatar())
                .append("password", this.getUsername()).toString();
    }
}
