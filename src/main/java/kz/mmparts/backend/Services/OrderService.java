package kz.mmparts.backend.Services;

import kz.mmparts.backend.DTO.OrderCreate;
import kz.mmparts.backend.Models.Order;
import kz.mmparts.backend.Models.Part;
import kz.mmparts.backend.Repository.OrderRepository;
import kz.mmparts.backend.Repository.PartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private PartRepository partRepository;

    public Order createOrder(OrderCreate data){
        Order order = new Order();

        List<Part> parts = partRepository.findAllById(data.getPartIds());

        order.setParts(parts);

        order.setFullName(data.getFullName());
        order.setPhone(data.getPhone());
        order.setIsDelivery(data.getIsDelivery());
        if(data.getIsDelivery()){
            order.setAddress(data.getAddress());
        }
        if (data.getEmail() != null){
            order.setEmail(data.getEmail());
        }
        if (data.getComments() != null){
            order.setComments(data.getComments());
        }

        return orderRepository.save(order);
    }
}
