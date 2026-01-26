package kz.mmparts.backend.Controllers;

import kz.mmparts.backend.Services.GenerationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generations")
@AllArgsConstructor
public class GenerationController {

    private GenerationService generationService;

    @GetMapping("/all/brands")
    public ResponseEntity<?> getAllBrands(){
        return ResponseEntity.ok(generationService.findAllBrands());
    }

    @GetMapping("/all/models/{brand}")
    public ResponseEntity<?> getAllModelsOfBrand(@PathVariable String brand){
        return ResponseEntity.ok(generationService.findAllModelsOfBrand(brand));
    }

    @GetMapping("/all/generations/{brand}/{model}")
    public ResponseEntity<?> getAllGenerationsOfBrandAndModel(
            @PathVariable String brand,
            @PathVariable String model
    ){
        return ResponseEntity.ok(generationService.findAllGenerationsOfBrandAndModel(brand, model));
    }
}
