package kz.mmparts.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PartUpdate extends PartCreate{

    private List<Long> oldImagesIds;
}
