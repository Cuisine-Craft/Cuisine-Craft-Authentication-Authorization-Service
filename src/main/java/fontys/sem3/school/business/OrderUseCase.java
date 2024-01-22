package fontys.sem3.school.business;

import fontys.sem3.school.domain.*;

import java.util.List;

public interface OrderUseCase {

    GetAllOrderHeaderResponse getAllOrderHeaders();

    GetAllOrderHeaderResponse getOrderHeadersbyCustomerid(long UserId);
    CreateOrderResponse createOrders(CreateOrderRequest request);
    GetAllOrderDetailResponse getOrderDetailsbyOrderHeaderid(long UserId);
    List<Object[]> getOrderDetailsbySellerid(long UserId);
    double getTotalSalesfromlastquarter();
}
