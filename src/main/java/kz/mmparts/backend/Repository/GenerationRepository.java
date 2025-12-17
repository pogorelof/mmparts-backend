package kz.mmparts.backend.Repository;

import kz.mmparts.backend.Models.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {
    Optional<Generation> findByBrandAndModelAndGeneration(String brand, String model, String generation);
}
