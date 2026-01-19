package kz.mmparts.backend.Services;

import kz.mmparts.backend.Repository.GenerationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenerationService {

    private GenerationRepository generationRepository;

    public List<String> findAllBrands(){
        return generationRepository.findDistinctBrands();
    }

    public List<String> findAllModelsOfBrand(String brand){
        return generationRepository.findUniqueModelsByBrand(brand);
    }

    public List<String> findAllGenerationsOfBrandAndModel(String brand, String model){
        return generationRepository.findUniqueGenerationsByBrandAndModel(brand, model);
    }
}
