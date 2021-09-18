package backvoteschallenge.entities.vote.entity;

import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public Vote(TypeVote typeVote, Associate associate) {
        this.typeVote = typeVote;
        this.associate = associate;
    }

    //    only hibernate
    public Vote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeVote getTypeVote() {
        return typeVote;
    }

    public void setTypeVote(TypeVote typeVote) {
        this.typeVote = typeVote;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id) && typeVote == vote.typeVote && Objects.equals(createdAt, vote.createdAt) && Objects.equals(associate, vote.associate) && Objects.equals(order, vote.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeVote, createdAt, associate, order);
    }
}