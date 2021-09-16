package backvoteschallenge.entities.vote.entity;

import backvoteschallenge.entities.order.entity.Order;
import backvoteschallenge.entities.associate.entity.associate.Associate;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_vote", nullable = false)
    private TypeVote typeVote;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    @Column(name = "created_at", nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "associate_id", nullable = false)
    private Associate associate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //    only hibernate
    public Vote() {
    }

}