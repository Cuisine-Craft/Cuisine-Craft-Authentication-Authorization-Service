package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.OrderUseCase;
import fontys.sem3.school.business.exception.InvalidFoodException;
import fontys.sem3.school.domain.*;
import fontys.sem3.school.persistence.FoodRepository;
import fontys.sem3.school.persistence.OrderDetailRepository;
import fontys.sem3.school.persistence.OrderHeaderRepository;
import fontys.sem3.school.persistence.entity.FoodEntity;
import fontys.sem3.school.persistence.entity.OrderDetailEntity;
import fontys.sem3.school.persistence.entity.OrderHeaderEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderUseCaseImpl implements OrderUseCase {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final FoodRepository foodRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy, HH:mm:ss");

    @Override
    public GetAllOrderHeaderResponse getAllOrderHeaders() {

        List<OrderHeader> orders = orderHeaderRepository.findAll()
                .stream()
                .map(OrderHeaderConverter::convert)
                .toList();
        return GetAllOrderHeaderResponse.builder()
                .orderheaders(orders)
                .build();
    }
    @Override
    public GetAllOrderHeaderResponse getOrderHeadersbyCustomerid(long userId) {

        List<OrderHeader> orders = orderHeaderRepository.findByUserId(userId)
                .stream()
                .map(OrderHeaderConverter::convert)
                .toList();
        return GetAllOrderHeaderResponse.builder()
                .orderheaders(orders)
                .build();
    }
    @Override
    @Transactional
    public CreateOrderResponse createOrders(CreateOrderRequest request) {
        try {
            String formattedTimestamp = LocalDateTime.now().format(formatter);
            OrderHeaderEntity orderHeaderEntity = OrderHeaderEntity.builder()
                    .userId(request.getUserId())
                    .total(request.getTotal())
                    .timestamp(formattedTimestamp)
                    .build();
            OrderHeaderEntity savedOrderHeader = orderHeaderRepository.save(orderHeaderEntity);

            if (savedOrderHeader != null) {
                long orderHeaderId = savedOrderHeader.getId();

                // Create OrderDetailEntity instances and associate them with the OrderHeader
                List<OrderDetailEntity> orderDetailEntities = request.getOrderItems().stream()
                        .map(orderItem -> {
                            Optional<FoodEntity> food = foodRepository.findById(orderItem.getFoodid());

                            if (food.isPresent()) {
                                // Check if totalsales is null, and set it to 0 if needed
                                if (food.get().getTotalsales() == null) {
                                    food.get().setTotalsales(0L);
                                }

                                // Update totalsales
                                food.get().setTotalsales(food.get().getTotalsales() + orderItem.getAmount());
                                foodRepository.save(food.get());

                                // Create OrderDetailEntity
                                return OrderDetailEntity.builder()
                                        .food(food.get())
                                        .amount(orderItem.getAmount())
                                        .subtotal(orderItem.getAmount() * food.get().getPrice())
                                        .specialRequest(orderItem.getSpecialRequest())
                                        .orderHeader(savedOrderHeader)
                                        .build();
                            } else {
                                // Handle the case where food is not found
                                throw new InvalidFoodException();
                            }
                        })
                        .toList();

                // Save OrderDetailEntities
                orderDetailRepository.saveAll(orderDetailEntities);

                // Return a CreateOrderResponse with the necessary information
                return CreateOrderResponse.builder()
                        .Id(orderHeaderId)
                        .build();
            } else {
                // Handle the case where the order header was not saved
                throw new RuntimeException("Failed to save the order header.");
            }
        } catch (InvalidFoodException e) {
            // Log the exception or handle it based on your application's requirements
            throw e;
        } catch (Exception e) {
            // Log the exception or handle it based on your application's requirements
            throw new RuntimeException("Failed to create the order.", e);
        }
    }


    @Override
    public GetAllOrderDetailResponse getOrderDetailsbyOrderHeaderid(long userId) {
        // Implement logic to fetch orders by user ID
        List<OrderDetail> orderdetails = orderDetailRepository.findByOrderHeader_Id(userId)
                .stream()
                .map(OrderDetailConverter::convert)
                .toList();
        return GetAllOrderDetailResponse.builder()
                .orderdetails(orderdetails).build();
    }
    @Override
    public double getTotalSalesfromlastquarter() {
        // Implement logic to fetch orders by user ID
        LocalDateTime endDate = LocalDateTime.now();  // Assuming today is the end date
        LocalDateTime startDate = endDate.minusMonths(3);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy, HH:mm:ss");
        // Convert the dates to LocalDateTime objects
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);

        // Invoke the repository method to get the total sales for the last quarter
        Double totalSales = orderHeaderRepository.calculateTotalSalesForLastQuarter(startDateStr, endDateStr);

        // Return the total sales, or 0 if not present
        return totalSales != null ? totalSales : 0;
    }





}
