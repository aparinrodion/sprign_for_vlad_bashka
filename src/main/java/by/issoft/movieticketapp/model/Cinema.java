package by.issoft.movieticketapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "cinemas")
@ToString
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cinema cinema = (Cinema) o;
        if (!Objects.equals(this.id, cinema.id)) return false;
        if (!Objects.equals(this.name, cinema.name)) return false;
        return Objects.equals(this.address, cinema.address);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
