package kz.mmparts.backend.Controllers.Admin;

import kz.mmparts.backend.DTO.PartCreate;
import kz.mmparts.backend.DTO.PartUpdate;
import kz.mmparts.backend.Models.Part;
import kz.mmparts.backend.Repository.PartRepository;
import kz.mmparts.backend.Services.PartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin/parts")
@AllArgsConstructor
public class PartAdminController {

    private PartService partService;
    private PartRepository partRepository;

    @PostMapping("/create")
    public Part createPart(@RequestPart("part") PartCreate partCreate,
                           @RequestPart("images") List<MultipartFile> images) {
        return partService.createPart(partCreate, images);
    }

    @PutMapping("/update/{id}")
    public Part updatePart(
            @RequestPart("part")PartUpdate partUpdate,
            @RequestPart(value = "images", required = false) List<MultipartFile> newImages,
            @PathVariable Long id
            ){
        Part updatedPart = partService.updatePart(id, partUpdate, newImages);
        return updatedPart;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePart(@PathVariable Long id){
        Part part = partRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found")
        );
        partRepository.delete(part);
    }

    @PostMapping("/toggle/{id}")
    public void toggleInStock(@PathVariable Long id){
        Part part = partRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found")
        );
        partService.toggleInStock(part);
    }
}
