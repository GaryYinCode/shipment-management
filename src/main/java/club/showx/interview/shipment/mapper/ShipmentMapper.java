package club.showx.interview.shipment.mapper;

import club.showx.interview.shipment.entity.Shipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
@Mapper
public interface ShipmentMapper extends Serializable {

    public int create(Shipment shipment);

    public List<Shipment> queryAll();

    public List<Shipment> queryByIds(@Param("ids") Collection<String> ids);

    public List<Shipment> queryByTradeId(@Param("tradeId") String tradeId);

    public List<Shipment> queryBySplitId(@Param("splitId") String splitId);

    public List<Shipment> queryByMergedId(@Param("mergedId") String mergedId);

    public Shipment getById(@Param("id") String id);

    public int updateQuantity(@Param("id") String id, @Param("quantity") int quantity);

    public int updateOperate(@Param("id") String id, @Param("type") int type, @Param("splitId") String splitId, @Param("operateType") int operateType, @Param("mergedId") String mergedId);
}
