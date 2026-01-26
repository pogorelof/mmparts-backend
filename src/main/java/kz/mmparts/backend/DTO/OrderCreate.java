package kz.mmparts.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreate {

    private String fullName;
    private String phone;
    private String email;
    private String comments;
    private Boolean isDelivery;
    private String address;
    private List<Long> partIds;
}
