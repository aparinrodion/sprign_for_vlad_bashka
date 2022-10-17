package by.issoft.movieticketapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "movie_events")
@ToString
public class MovieEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_room_id")
    private MovieRoom movieRoom;
    @Column(name = "film_name")
    private String filmName;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieEvent that = (MovieEvent) o;
        if (!Objects.equals(this.id, that.id)) return false;
        if (!Objects.equals(this.movieRoom, that.movieRoom)) return false;
        if (!Objects.equals(this.filmName, that.filmName)) return false;
        return Objects.equals(this.dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
