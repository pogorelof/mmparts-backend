package kz.mmparts.backend.Controllers;

import kz.mmparts.backend.DTO.OrderCreate;
import kz.mmparts.backend.Repository.OrderRepository;
import kz.mmparts.backend.Services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreate data){
        return ResponseEntity.ok(orderService.createOrder(data));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrders(Pageable pageable){
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return ResponseEntity.ok(orderRepository.findAll(sortedPageable));
    }
}
