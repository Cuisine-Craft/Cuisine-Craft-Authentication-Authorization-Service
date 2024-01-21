package fontys.sem3.school.business.impl;
import fontys.sem3.school.business.UserValidator;
import fontys.sem3.school.business.exception.InvalidUserException;
import fontys.sem3.school.business.exception.UserNotFoundException;
import fontys.sem3.school.domain.*;
import fontys.sem3.school.persistence.CuisineRepository;
import fontys.sem3.school.persistence.FoodRepository;
import fontys.sem3.school.persistence.UserRepository;
import fontys.sem3.school.persistence.entity.CuisineEntity;
import fontys.sem3.school.persistence.entity.FoodEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class FoodUseCaseImplTest {

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private CuisineRepository cuisineRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator foodIdValidator;

    @InjectMocks
    private FoodUseCaseImpl foodUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foodUseCase = new FoodUseCaseImpl(foodRepository, cuisineRepository, foodIdValidator, userRepository);
    }

    @Test
    void testCreateFood_Success() {
        // Arrange
        CreateFoodRequest createFoodRequest = CreateFoodRequest.builder()
                .sellerid(1L)
                .name("Test Food")
                .description("Description")
                .pictureUrl("https://example.com/food.jpg")
                .price(10.0)
                .cuisineid(1L)
                .build();

        UserEntity mockSeller = UserEntity.builder().id(1L).build();
        CuisineEntity mockCuisine = CuisineEntity.builder().id(1L).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockSeller));
        when(cuisineRepository.findById(1L)).thenReturn(Optional.of(mockCuisine));
        when(foodRepository.save(any(FoodEntity.class))).thenReturn(FoodEntity.builder().id(1L).build());

        // Act
        CreateFoodResponse response = foodUseCase.createFood(createFoodRequest);

        // Assert
        assertEquals(1L, response.getId());

        // Verify that save method was called once
        verify(foodRepository, times(1)).save(any(FoodEntity.class));
    }
    @Test
    void testCreateFoodWithInvalidSeller() {
        // Arrange
        CreateFoodRequest createFoodRequest = CreateFoodRequest.builder()
                .sellerid(1L) // Assuming this user does not exist
                .name("Test Food")
                .description("Description")
                .pictureUrl("https://example.com/food.jpg")
                .price(10.0)
                .cuisineid(1L)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> foodUseCase.createFood(createFoodRequest));

        // Verify that save method was not called
        verify(foodRepository, never()).save(any(FoodEntity.class));
    }

    @Test
    void testCreateFoodWithInvalidCuisine() {
        // Arrange
        CreateFoodRequest createFoodRequest = CreateFoodRequest.builder()
                .sellerid(1L)
                .name("Test Food")
                .description("Description")
                .pictureUrl("https://example.com/food.jpg")
                .price(10.0)
                .cuisineid(1L) // Assuming this cuisine does not exist
                .build();

        UserEntity mockSeller = UserEntity.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockSeller));
        when(cuisineRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> foodUseCase.createFood(createFoodRequest));

        // Verify that save method was not called
        verify(foodRepository, never()).save(any(FoodEntity.class));
    }

    @Test
    void testSoftDeleteFood() {
        // Arrange
        long foodId = 1L;

        // Act
        foodUseCase.softdeleteFood(foodId);

        // Verify that updateStatusByFoodId method was called once
        verify(foodRepository, times(1)).updateStatusByFoodId(foodId, false);
    }
    @Test
    void testGetFood() {
        // Arrange
        long foodId = 1L;
        when(foodRepository.findById(foodId)).thenReturn(Optional.of(FoodEntity.builder().id(foodId).build()));

        // Act
        Optional<Food> result = foodUseCase.getFood(foodId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(foodId, result.get().getId());

        // Verify that findById method was called once
        verify(foodRepository, times(1)).findById(foodId);
    }

    @Test
    void testGetFoodsByCuisineId() {
        // Arrange
        long cuisineId = 1L;
        when(foodRepository.findByCuisine_IdAndStatus(cuisineId, true))
                .thenReturn(List.of(FoodEntity.builder().id(1L).build()));

        // Act
        GetAllFoodsResponse result = foodUseCase.getFoodsbyCuisineId(cuisineId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getFoods().size());

        // Verify that findByCuisine_IdAndStatus method was called once
        verify(foodRepository, times(1)).findByCuisine_IdAndStatus(cuisineId, true);
    }
    @Test
    void testGetFoodNotFound() {
        // Arrange
        long nonExistingFoodId = 999L;
        when(foodRepository.findById(nonExistingFoodId)).thenReturn(Optional.empty());

        // Act
        Optional<Food> result = foodUseCase.getFood(nonExistingFoodId);

        // Assert
        assertTrue(result.isEmpty());

        // Verify that findById method was called once
        verify(foodRepository, times(1)).findById(nonExistingFoodId);
    }

    @Test
    void testGetFoodsMostSoldFood() {
        // Arrange
        when(foodRepository.findTop5MostSoldFoods())
                .thenReturn(List.of(FoodEntity.builder().id(1L).build()));

        // Act
        GetAllFoodsResponse result = foodUseCase.getFoodsmostsoldfood();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getFoods().size());

        // Verify that findTop5MostSoldFoods method was called once
        verify(foodRepository, times(1)).findTop5MostSoldFoods();
    }

    @Test
    void testUpdateFood() {
        // Arrange
        UpdateFoodRequest updateFoodRequest = UpdateFoodRequest.builder()
                .id(1L)
                .name("Updated Food")
                .description("Updated Description")
                .build();

        FoodEntity existingFoodEntity = FoodEntity.builder().id(1L).build();
        when(foodRepository.findById(updateFoodRequest.getId())).thenReturn(Optional.of(existingFoodEntity));

        // Act
        foodUseCase.updateFood(updateFoodRequest);

        // Assert
        assertEquals("Updated Food", existingFoodEntity.getName());
        assertEquals("Updated Description", existingFoodEntity.getDescription());

        // Verify that findById and save methods were called once
        verify(foodRepository, times(1)).findById(updateFoodRequest.getId());
        verify(foodRepository, times(1)).save(existingFoodEntity);
    }


    @Test
    void testUpdateFoodWithInvalidId() {
        // Arrange
        UpdateFoodRequest updateFoodRequest = UpdateFoodRequest.builder()
                .id(1L) // Assuming this food does not exist
                .name("Updated Food")
                .description("Updated Description")
                .build();

        when(foodRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidUserException.class, () -> foodUseCase.updateFood(updateFoodRequest));

        // Verify that save method was not called
        verify(foodRepository, never()).save(any(FoodEntity.class));
    }



}
