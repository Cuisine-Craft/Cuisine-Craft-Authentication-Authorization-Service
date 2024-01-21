package fontys.sem3.school.persistence;

import fontys.sem3.school.domain.OrderHeader;
import fontys.sem3.school.persistence.entity.ChatEntity;
import fontys.sem3.school.persistence.entity.FoodEntity;
import fontys.sem3.school.persistence.entity.OrderDetailEntity;
import fontys.sem3.school.persistence.entity.OrderHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderHeaderRepository extends JpaRepository<OrderHeaderEntity, Long> {

    Optional<OrderHeaderEntity> findById(Long OrderHeaderid);
    List<OrderHeaderEntity> findByUserId(Long OrderHeaderid);
    @Query(value = "SELECT COALESCE(SUM(o.total), 0) FROM OrderHeaderEntity o WHERE STR_TO_DATE(o.timestamp, '%m/%d/%Y, %H:%i:%s') BETWEEN STR_TO_DATE(?1, '%m/%d/%Y, %H:%i:%s') AND STR_TO_DATE(?2, '%m/%d/%Y, %H:%i:%s')")
    Double calculateTotalSalesForLastQuarter(String startDate, String endDate);



}
