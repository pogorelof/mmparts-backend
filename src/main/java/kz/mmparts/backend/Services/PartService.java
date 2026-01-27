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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PartService {

    @Value("${app.upload-media-type}")
    private String uploadMediaType;

    private final Cloudinary cloudinary;
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
            PartImage partImage = new PartImage();

            if (uploadMediaType.equals("local")){
                Path filePath = Paths.get(uploadDir + filename);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, image.getBytes());
                partImage.setUrl("/uploads/" + filename);
            }else if(uploadMediaType.equals("cloudinary")){
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                partImage.setUrl(uploadResult.get("url").toString());
            }

            savedImages.add(partImage);
        }

        return savedImages;
    }

    public void toggleInStock(Part part){
        part.setInStock(!part.getInStock());
        partRepository.save(part);
    }
}

