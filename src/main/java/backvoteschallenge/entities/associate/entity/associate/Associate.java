package backvoteschallenge.entities.associate.entity.associate;

import javax.persistence.*;

@Entity(name = "associate")
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", nullable = false)
    private String document;

    // only hibernate
    public Associate() {
    }
}
