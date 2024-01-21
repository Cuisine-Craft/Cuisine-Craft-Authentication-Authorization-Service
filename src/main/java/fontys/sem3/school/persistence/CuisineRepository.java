
package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.CuisineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CuisineRepository extends JpaRepository<CuisineEntity, Long> {

    @Query("SELECT c.id, c.name, c.pictureUrl, SUM(f.totalsales) AS totalSales " +
            "FROM CuisineEntity c " +
            "LEFT JOIN FoodEntity f ON c.id = f.cuisine.id " +
            "GROUP BY c.id, c.name, c.pictureUrl " +
            "ORDER BY totalSales DESC")
    List<Object[]> aggregateCuisineTotalSales();
}

