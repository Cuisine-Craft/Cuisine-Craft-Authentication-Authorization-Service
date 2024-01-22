package fontys.sem3.school.business.impl;

import fontys.sem3.school.domain.*;
import fontys.sem3.school.persistence.FoodRepository;
import fontys.sem3.school.persistence.OrderDetailRepository;
import fontys.sem3.school.persistence.OrderHeaderRepository;
import fontys.sem3.school.persistence.UserRepository;
import fontys.sem3.school.persistence.entity.FoodEntity;
import fontys.sem3.school.persistence.entity.OrderDetailEntity;
import fontys.sem3.school.persistence.entity.OrderHeaderEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderUseCaseImplTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderHeaderRepository orderHeaderRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderUseCaseImpl orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCaseImpl(
                orderDetailRepository,
                orderHeaderRepository,
                foodRepository
        );
    }

    @Test
    void testCreateOrder() {
        // Arrange
        CreateOrderRequest request = CreateOrderRequest.builder()
                .userId(1L)
                .total(50.0)
                .orderItems(Collections.singletonList(
                        OrderDetailRequest.builder()
                                .foodid(1L)
                                .amount(2)
                                .specialRequest("No onions")
                                .build()
                ))
                .build();

        UserEntity mockUser = UserEntity.builder().id(1L).build();
        FoodEntity mockFood = FoodEntity.builder().id(1L).price(25.0).build();
        OrderHeaderEntity savedOrderHeader = OrderHeaderEntity.builder().id(1L).build();

        when(orderHeaderRepository.save(any(OrderHeaderEntity.class))).thenReturn(savedOrderHeader);
        when(foodRepository.findById(1L)).thenReturn(Optional.of(mockFood));

        // Act
        CreateOrderResponse response = orderUseCase.createOrders(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());

        // Verify that orderHeaderRepository.save was called once
        verify(orderHeaderRepository, times(1)).save(any(OrderHeaderEntity.class));

        // Verify that foodRepository.findById was called once
        verify(foodRepository, times(1)).findById(1L);

        // Verify that orderDetailRepository.saveAll was called once
        verify(orderDetailRepository, times(1)).saveAll(anyList());
    }
    @Test
    void createTestOrderRequest() {
        // Create a test order request with sample data
        List<OrderDetailRequest> orderItems = List.of(
                OrderDetailRequest.builder().foodid(1L).amount(2).specialRequest("Special Request 1").build(),
                OrderDetailRequest.builder().foodid(2L).amount(3).specialRequest("Special Request 2").build()
        );

        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .userId(123L)
                .total(150.0)
                .orderItems(orderItems)
                .build();

        assertNotNull(createOrderRequest);
        assertEquals(123L, createOrderRequest.getUserId());
        assertEquals(150.0, createOrderRequest.getTotal());
        assertEquals(orderItems, createOrderRequest.getOrderItems());
    }



    @Test
    void createTestFoodEntity() {
        // Create a test food entity with sample data
        FoodEntity foodEntity = createTestFoodEntity(1L, "Food 1", 10.0);

        assertNotNull(foodEntity);
        assertEquals(1L, foodEntity.getId());
        assertEquals("Food 1", foodEntity.getName());
        assertEquals(10.0, foodEntity.getPrice());
        assertEquals(0L, foodEntity.getTotalsales());
    }

    private FoodEntity createTestFoodEntity(long id, String name, double price) {
        // Create a test food entity with sample data
        return FoodEntity.builder()
                .id(id)
                .name(name)
                .price(price)
                .totalsales(0L)
                .build();
    }


    @Test
    void createTestOrderHeaderEntity() {
        // Create a test order header entity with sample data
        OrderHeaderEntity orderHeaderEntity = OrderHeaderEntity.builder()
                .id(1L)
                .userId(123L)
                .total(150.0)
                .timestamp("01/20/2024, 12:34:56")  // Replace with an actual timestamp
                .build();

        assertNotNull(orderHeaderEntity);
        assertEquals(1L, orderHeaderEntity.getId());
        assertEquals(123L, orderHeaderEntity.getUserId());
        assertEquals(150.0, orderHeaderEntity.getTotal());
        assertEquals("01/20/2024, 12:34:56", orderHeaderEntity.getTimestamp());

    }

    @Test
    void createTestOrderDetailEntities() {
        // Create test order detail entities with sample data
        FoodEntity foodEntity1 = createTestFoodEntity(1L, "Food 1", 10.0);
        FoodEntity foodEntity2 = createTestFoodEntity(2L, "Food 2", 15.0);
        OrderHeaderEntity orderHeaderEntity = OrderHeaderEntity.builder()
                .id(1L)
                .userId(123L)
                .total(150.0)
                .timestamp("01/20/2024, 12:34:56")  // Replace with an actual timestamp
                .build();

        OrderDetailEntity orderDetailEntity1 = OrderDetailEntity.builder()
                .food(foodEntity1)
                .amount(2)
                .subtotal(2 * foodEntity1.getPrice())
                .specialRequest("Special Request 1")
                .orderHeader(orderHeaderEntity)
                .build();

        OrderDetailEntity orderDetailEntity2 = OrderDetailEntity.builder()
                .food(foodEntity2)
                .amount(3)
                .subtotal(3 * foodEntity2.getPrice())
                .specialRequest("Special Request 2")
                .orderHeader(orderHeaderEntity)
                .build();
        List<OrderDetailEntity> orderDetailEntities= List.of(orderDetailEntity1, orderDetailEntity2);

        assertNotNull(orderDetailEntities);
        assertEquals(2, orderDetailEntities.size());
        assertEquals(orderDetailEntity1, orderDetailEntities.get(0));
        assertEquals(orderDetailEntity2, orderDetailEntities.get(1));
    }


    @Test
    void testGetOrderDetailsBySellerId() {
        // Arrange
        long sellerId = 1L;
        OrderDetailRepository mockOrderDetailRepository = mock(OrderDetailRepository.class);
        OrderUseCaseImpl orderUseCase = new OrderUseCaseImpl(
                mockOrderDetailRepository,
                orderHeaderRepository,
                foodRepository
        );

        // Mock the repository response
        List<Object[]> orderDetailsWithTimestamp = Arrays.asList(
                new Object[]{1L, "Product A", 10.0, Timestamp.valueOf("2023-01-01 12:00:00")},
                new Object[]{2L, "Product B", 15.0, Timestamp.valueOf("2023-01-02 14:30:00")}
        );
        when(mockOrderDetailRepository.findOrderDetailWithTimestampByOrderHeaderId(sellerId))
                .thenReturn(orderDetailsWithTimestamp);

        // Act
        List<Object[]> result = orderUseCase.getOrderDetailsbySellerid(sellerId);

        // Assert
        assertNotNull(result);
        assertEquals(orderDetailsWithTimestamp.size(), result.size());
        // Add more assertions based on your specific logic
    }

    @Test
    void testGetTotalSalesFromLastQuarter() {
        // Arrange
        OrderHeaderRepository mockOrderHeaderRepository = mock(OrderHeaderRepository.class);
        OrderUseCaseImpl orderUseCase = new OrderUseCaseImpl(
                orderDetailRepository,
                mockOrderHeaderRepository,
                foodRepository
        );

        // Mock the repository response
        Double totalSales = 1500.0;
        when(mockOrderHeaderRepository.calculateTotalSalesForLastQuarter(anyString(), anyString()))
                .thenReturn(totalSales);

        // Act
        double result = orderUseCase.getTotalSalesfromlastquarter();

        // Assert
        assertEquals(totalSales, result, 0.01); // Adjust delta based on your requirements
    }
}
