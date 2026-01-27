package kz.mmparts.backend.Controllers;


import kz.mmparts.backend.DTO.PartCreate;
import kz.mmparts.backend.Models.Part;
import kz.mmparts.backend.Repository.PartRepository;
import kz.mmparts.backend.Repository.PartSpecification;
import kz.mmparts.backend.Services.PartService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/parts")
@AllArgsConstructor
public class PartController {

    private PartService partService;
    private PartRepository partRepository;

    @GetMapping("/{id}")
    public Part getById(@PathVariable Long id){
        return partService.findById(id);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllParts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String generation,
            @RequestParam(required = false) String search,

            Pageable pageable
    ){

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Specification<Part> spec = Specification.where((root, query, cb) -> null);

        if (brand != null) spec = spec.and(PartSpecification.hasBrand(brand));
        if (model != null) spec = spec.and(PartSpecification.hasModel(model));
        if (generation != null) spec = spec.and(PartSpecification.hasGeneration(generation));

        if (search != null && !search.trim().isEmpty()){
            spec = spec.and(PartSpecification.searchByKeyword(search));
        }

        return ResponseEntity.ok(partRepository.findAll(spec, sortedPageable));
    }
}


