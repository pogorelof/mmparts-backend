package kz.mmparts.backend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(
        name = "generations",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_car_gen_brand_model_generation",
                columnNames = {"brand", "model", "generation"}
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String brand;
    @Column(nullable=false) private String model;
    @Column(nullable=false) private String generation;

    @ManyToMany(mappedBy = "generations")
    @JsonBackReference
    private Set<Part> parts;
}
