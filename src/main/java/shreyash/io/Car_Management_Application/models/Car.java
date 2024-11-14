package shreyash.io.Car_Management_Application.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String carType;
    private String company;
    private String dealer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
}
