package backvoteschallenge.entities.session.entity.Session;

import backvoteschallenge.entities.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    @Column(name = "created_at", nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @Column(name = "session_open")
    private Boolean open = true;

    public Session(Order order, LocalDateTime finishedAt) {
        this.order = order;
        this.finishedAt = finishedAt;
    }

    public boolean isOpen() {
        this.open = LocalDateTime.now().isBefore(finishedAt);
        return open;
    }

    // only hibernate
    public Session() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
