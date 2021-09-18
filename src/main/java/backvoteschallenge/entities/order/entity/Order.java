package backvoteschallenge.entities.order.entity;


import backvoteschallenge.entities.associate.entity.associate.Associate;
import backvoteschallenge.entities.vote.entity.Vote;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "theme", nullable = false)
    private String theme;

    @NotBlank
    @Column(name = "editor", nullable = false)
    private String editor;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    @Column(name = "created_at", nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> whishes = new ArrayList<>();

    public Order(String theme, String editor) {
        this.theme = theme;
        this.editor = editor;
    }

    //    only hibernate
    public Order() {
    }

    public void addVote(Vote vote) {
        this.whishes.add(vote);
        vote.setOrder(this);
    }

    public boolean associateAlreadyVoted(Associate associate) {
        return this.getWhishes()
                .stream()
                .anyMatch(vote -> vote
                        .getAssociate()
                        .getDocument()
                        .equals(associate.getDocument()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Vote> getWhishes() {
        return whishes;
    }

}
