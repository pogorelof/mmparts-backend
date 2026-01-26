package kz.mmparts.backend.Controllers.Admin;

import kz.mmparts.backend.DTO.PartCreate;
import kz.mmparts.backend.Models.Part;
import kz.mmparts.backend.Repository.PartRepository;
import kz.mmparts.backend.Services.PartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
