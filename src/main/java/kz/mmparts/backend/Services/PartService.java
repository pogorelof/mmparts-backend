package kz.mmparts.backend.Services;


import jakarta.transaction.Transactional;
import kz.mmparts.backend.DTO.GenerationDto;
import kz.mmparts.backend.DTO.PartCreate;
import kz.mmparts.backend.Models.Generation;
import kz.mmparts.backend.Models.Part;
import kz.mmparts.backend.Models.PartImage;
import kz.mmparts.backend.Repository.GenerationRepository;
import kz.mmparts.backend.Repository.PartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PartService {

    private final PartRepository partRepository;
    private final GenerationRepository generationRepository;

    public Part findById(Long id){
        return partRepository.findById(id).orElseGet(()->null);
    }

    @Transactional
    public Part createPart(PartCreate partDto, List<MultipartFile> images){
        Part part = new Part();
        part.setTitle(partDto.getTitle());
        part.setDescription(partDto.getDescription());
        part.setPrice(partDto.getPrice());

        List<PartImage> partImages = null;
        try {
            partImages = saveImages(images);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        partImages.forEach(i -> i.setPart(part));
        part.setImages(partImages);

        for (GenerationDto generationDto : partDto.getGenerations()){
            Generation generation = findOrCreateGeneration(generationDto);
            part.getGenerations().add(generation);
        }


        return partRepository.save(part);
    }

    public Generation findOrCreateGeneration(GenerationDto generationDto){
        return generationRepository.findByBrandAndModelAndGeneration(
                generationDto.getBrand(),
                generationDto.getModel(),
                generationDto.getGeneration()).orElseGet(() -> {
                    Generation g = new Generation();
                    g.setBrand(generationDto.getBrand());
                    g.setModel(generationDto.getModel());
                    g.setGeneration(generationDto.getGeneration());
                    return generationRepository.save(g);
        });
    }

    public List<PartImage> saveImages(List<MultipartFile> images) throws IOException {
        String uploadDir = "uploads/";
        List<PartImage> savedImages = new ArrayList<>();

        for (MultipartFile image : images){
            String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());

            PartImage partImage = new PartImage();
            partImage.setUrl("/uploads/" + filename);

            savedImages.add(partImage);
        }

        return savedImages;
    }

    public void toggleInStock(Part part){
        part.setInStock(!part.getInStock());
        partRepository.save(part);
    }
}

