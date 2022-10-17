package by.issoft.movieticketapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "movie_event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MovieEvent movieEvent;
    @Column(name = "seat_row")
    private Integer seatRow;
    @Column(name = "seat_number")
    private Integer seatNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ticket ticket = (Ticket) o;
        if (!Objects.equals(this.id, ticket.id)) return false;
        if (!Objects.equals(this.movieEvent, ticket.movieEvent)) return false;
        if (!Objects.equals(this.seatRow, ticket.seatRow)) return false;
        return Objects.equals(this.seatNumber, ticket.seatNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
