package fontys.sem3.school.business;

import fontys.sem3.school.domain.*;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FoodUseCase {
    void softdeleteFood(long cuisineId);

    GetAllFoodsResponse getFoods();

    CreateFoodResponse createFood(CreateFoodRequest request);
    Optional<Food> getFood(long UserId);
    GetAllFoodsResponse getFoodsbyCuisineId(long cuisineId);

    GetAllFoodsResponse getFoodsmostsoldfood();

    void updateFood(UpdateFoodRequest request);


}
