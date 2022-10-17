package by.issoft.movieticketapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "movie_rooms")
@ToString
public class MovieRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "movie_room_number")
    private Integer movieRoomNumber;
    @Column(name = "capacity")
    private Integer capacity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieRoom movieRoom = (MovieRoom) o;
        if (!Objects.equals(this.id, movieRoom.id)) return false;
        if (!Objects.equals(this.movieRoomNumber, movieRoom.movieRoomNumber)) return false;
        if (!Objects.equals(this.capacity, movieRoom.capacity)) return false;
        return Objects.equals(this.cinema, movieRoom.cinema);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
