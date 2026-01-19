package kz.mmparts.backend.Repository;

import kz.mmparts.backend.Models.Generation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {
    Optional<Generation> findByBrandAndModelAndGeneration(String brand, String model, String generation);

    @Query("SELECT DISTINCT brand from Generation")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT model from Generation where brand = :brand")
    List<String> findUniqueModelsByBrand(@Param("brand") String brand);

    @Query("SELECT DISTINCT generation from Generation where brand = :brand and model = :model")
    List<String> findUniqueGenerationsByBrandAndModel(@Param("brand") String brand,
                                                      @Param("model") String model);
}
