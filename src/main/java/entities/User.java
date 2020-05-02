package entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;


@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    private String password;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User(){
        this("","");
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Optional<String> getUserName() {
        return ofNullable(userName);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Optional<String> getPassword() {
        return ofNullable(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
