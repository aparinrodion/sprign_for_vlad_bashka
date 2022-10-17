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
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    @Column(name = "date_time")
    private LocalDateTime creationDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        if (!Objects.equals(this.id, order.id)) return false;
        if (!Objects.equals(this.user, order.user)) return false;
        if (!Objects.equals(this.ticket, order.ticket)) return false;
        return Objects.equals(this.creationDateTime, order.creationDateTime);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}