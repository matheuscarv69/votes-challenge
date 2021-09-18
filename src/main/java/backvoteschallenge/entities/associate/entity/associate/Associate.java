package backvoteschallenge.entities.associate.entity.associate;

import javax.persistence.*;

@Entity(name = "associate")
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    // only hibernate
    public Associate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocument() {
        return document;
    }
}
