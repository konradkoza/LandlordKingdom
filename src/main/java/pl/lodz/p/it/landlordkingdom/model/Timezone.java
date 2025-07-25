package pl.lodz.p.it.landlordkingdom.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity(name = "timezones")
@Getter
public class Timezone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, updatable = false, length = 50)
    private String name;
}
