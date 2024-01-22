package fontys.sem3.school.persistence;

import fontys.sem3.school.domain.GetAllFoodsResponse;
import fontys.sem3.school.persistence.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
//    boolean existsByCode(String code);

    @Modifying
    @Query("UPDATE FoodEntity f SET f.status = :status WHERE f.id = :foodId")
    void updateStatusByFoodId(long foodId, boolean status);
    @Query(value = "SELECT f FROM FoodEntity f ORDER BY f.totalsales DESC")
    List<FoodEntity> findTop5MostSoldFoods();
    List<FoodEntity> findByCuisine_IdAndStatus(Long cuisineId, boolean status);
}
