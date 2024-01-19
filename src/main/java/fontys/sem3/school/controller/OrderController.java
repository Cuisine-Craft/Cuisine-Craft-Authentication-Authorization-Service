package fontys.sem3.school.controller;

import fontys.sem3.school.business.CuisineUseCase;
import fontys.sem3.school.business.OrderUseCase;
import fontys.sem3.school.domain.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderUseCase orderUseCase;
    @GetMapping()
    @RolesAllowed({"Admin"})
    public GetAllOrderHeaderResponse getAllOrderHeaders() {
        return orderUseCase.getAllOrderHeaders();
    }

    @GetMapping("/header/{userId}")
    @RolesAllowed({"Customer"})
    public GetAllOrderHeaderResponse getOrderHeadersByCustomerId(@PathVariable long userId) {
        return orderUseCase.getOrderHeadersbyCustomerid(userId);
    }

    @PostMapping()
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderUseCase.createOrders(request);
    }

    @GetMapping("/details/{orderHeaderId}")
    public GetAllOrderDetailResponse getOrderDetailsByOrderHeaderId(@PathVariable long orderHeaderId) {
        return orderUseCase.getOrderDetailsbyOrderHeaderid(orderHeaderId);
    }

}
