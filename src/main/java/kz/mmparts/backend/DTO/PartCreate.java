package kz.mmparts.backend.DTO;


import lombok.Data;

import java.util.List;

@Data
public class PartCreate {

    private String title;
    private String description;
    private Double price;

    private List<GenerationDto> generations;
}
