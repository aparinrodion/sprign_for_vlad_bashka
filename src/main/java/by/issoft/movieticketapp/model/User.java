package by.issoft.movieticketapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String username;
    @Column
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        if (!Objects.equals(this.id, user.id)) return false;
        if (!Objects.equals(this.email, user.email)) return false;
        if (!Objects.equals(this.firstName, user.firstName)) return false;
        if (!Objects.equals(this.lastName, user.lastName)) return false;
        if (!Objects.equals(this.phoneNumber, user.phoneNumber)) return false;
        return Objects.equals(this.username, user.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}