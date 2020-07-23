package club.showx.interview.shipment.mapper;

import club.showx.interview.shipment.entity.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Mapper
public interface TradeMapper extends Serializable {

    public int create(Trade trade);

    public List<Trade> queryAll();

    public Trade getById(@Param("id") String id);

    public int updateQuantity(@Param("id") String id, @Param("quantity") int quantity);
}
