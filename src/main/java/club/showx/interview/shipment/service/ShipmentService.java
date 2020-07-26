package club.showx.interview.shipment.service;

import club.showx.interview.shipment.WebServiceException;
import club.showx.interview.shipment.entity.OperateType;
import club.showx.interview.shipment.entity.Shipment;
import club.showx.interview.shipment.entity.ShipmentType;
import club.showx.interview.shipment.entity.Trade;
import club.showx.interview.shipment.mapper.ShipmentMapper;
import club.showx.interview.shipment.mapper.TradeMapper;
import club.showx.interview.shipment.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService implements Serializable {
    private static final Logger logger = Logger.getLogger(ShipmentService.class);

    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private ShipmentMapper shipmentMapper;

    @PostConstruct
    private void init() {
        //just for init the h2 database.
        try {
            Trade trade = new Trade();

            trade.setQuantity(600);
            trade.setTitle("Auto initialized Trade");

            trade.setCreateDate(new Date());
            trade.setCreateUserId("user-1000");
            trade.setCreateIp("127.0.0.1");

            trade = createTrade(trade);
            if (trade != null) {
                List<Shipment> shipments = queryShipmentsByTrade(trade.getId());

                for (Shipment shipment : shipments) {
                    if (shipment.getTradeId().equals(trade.getId())) {
                        List<Integer> quantities = new ArrayList<Integer>();
                        quantities.add(100);
                        quantities.add(200);
                        quantities.add(300);

                        //
                        List<String> mergedIds = new ArrayList<String>();

                        int idx = 0;
                        List<Shipment> splitShipments = splitShipment(shipment.getId(), quantities);
                        for (Shipment splitShipment : splitShipments) {
                            mergedIds.add(splitShipment.getId());

                            idx++;

                            if (idx >= 2) {
                                break;
                            }
                        }

                        mergeShipments(trade.getId(), mergedIds);
                    }
                }
            }
        } catch (WebServiceException e) {

        }
    }

    //trade related apis
    @Transactional
    public Trade createTrade(Trade trade) throws WebServiceException {
        Trade returnValue = null;

        trade.setId(UUID.randomUUID().toString());

        //1. insert the trade
        if (tradeMapper.create(trade) > 0) {
            returnValue = trade;

            //2. insert the root shipment
            Shipment shipment = new Shipment();

            //
            shipment.setCreateDate(returnValue.getCreateDate());
            shipment.setCreateUserId(returnValue.getCreateUserId());
            shipment.setCreateIp(returnValue.getCreateIp());

            shipment.setId(UUID.randomUUID().toString());
            shipment.setTradeId(returnValue.getId());

            shipment.setQuantity(returnValue.getQuantity());

            //
            shipment.setType(ShipmentType.ROOT.getNo());
            shipment.setOperateType(OperateType.NONE.getNo());

            //
            shipmentMapper.create(shipment);
        }

        return returnValue;
    }

    public List<Trade> queryTradeAll() throws WebServiceException {
        return tradeMapper.queryAll();
    }

    public Trade getTradeById(String tradeId) throws WebServiceException {
        return tradeMapper.getById(tradeId);
    }

    @Transactional
    public boolean changeTradeQuantity(String tradeId, int quantity) throws WebServiceException {
        boolean returnValue = false;

        //1. get the trade and check its quantity.
        Trade trade = tradeMapper.getById(tradeId);
        if (trade == null) {
            throw new WebServiceException(WebServiceException.SHIPMENT_TRADE_NOT_EXIST, "The trade isn't exist.");
        }

        //2. check the quantity
        if (trade.getQuantity() != quantity) {
            //3. change shipments quantity
            List<Shipment> tradeShipments = shipmentMapper.queryByTradeId(trade.getId());
            for (Shipment shipment : tradeShipments) {
                shipmentMapper.updateQuantity(shipment.getId(), quantity * shipment.getQuantity() / trade.getQuantity());
            }

            //4. chang the trade quantity.
            tradeMapper.updateQuantity(trade.getId(), quantity);

            //
            returnValue = true;
        }


        return returnValue;
    }

    //the shipment related apis.
    public List<Shipment> queryShipmentsByTrade(String tradeId) throws WebServiceException {
        //1. get the shipment and check the quantity
        Trade trade = tradeMapper.getById(tradeId);
        if (trade == null) {
            throw new WebServiceException(WebServiceException.SHIPMENT_TRADE_NOT_EXIST, "The trade isn't exist.");
        }

        return shipmentMapper.queryByTradeId(tradeId);
    }

    @Transactional
    public List<Shipment> splitShipment(String shipmentId, Collection<Integer> splitQuantities) throws WebServiceException {
        List<Shipment> returnValue = new ArrayList<Shipment>();

        //1. get the shipment and check the quantity
        Shipment shipment = shipmentMapper.getById(shipmentId);
        if (shipment == null) {
            throw new WebServiceException(WebServiceException.SHIPMENT_NOT_EXIST, "Shipment isn't exist.");
        }

        if (Utils.sum(splitQuantities) == shipment.getQuantity()) {
            //2. create the split shipments
            for (int quantity : splitQuantities) {
                Shipment splitShipment = new Shipment();

                //
                splitShipment.setId(UUID.randomUUID().toString());
                splitShipment.setTradeId(shipment.getTradeId());

                splitShipment.setType(ShipmentType.SPLIT.getNo());
                splitShipment.setSplitId(shipment.getId());

                splitShipment.setOperateType(OperateType.NONE.getNo());

                splitShipment.setQuantity(quantity);

                //
                splitShipment.setCreateDate(new Date());

                //
                if (shipmentMapper.create(splitShipment) > 0) {
                    returnValue.add(splitShipment);
                }
            }

            //3. update the split shipment's operate type.
            shipmentMapper.updateOperate(
                    shipment.getId(),
                    shipment.getType(), shipment.getSplitId(),
                    OperateType.SPLIT.getNo(), null
            );
        } else {
            throw new WebServiceException(WebServiceException.SHIPMENT_SPLIT_QUANTITY_NOT_EQUALS, "Split quantities sum is not equals to shipment quantity.");
        }

        return returnValue;
    }

    @Transactional
    public Shipment mergeShipments(String tradeId, Collection<String> mergedIds) throws WebServiceException {
        Shipment returnValue = null;

        if (mergedIds == null || mergedIds.size() < 2) {
            throw new WebServiceException(WebServiceException.SHIPMENT_MERGED_SIZE_LESS_THAN_2, "Merged shipments size is less than 2.");
        }

        //1. query the shipments to merged
        List<String> ids = new ArrayList<String>();
        ids.addAll(mergedIds);

        List<Shipment> mergedShipments = shipmentMapper.queryByIds(mergedIds);
        if (mergedShipments != null && mergedShipments.size() > 0) {
            int quantity = 0;
            String mergedId = UUID.randomUUID().toString();

            //2. update the merged shipment's operate type.
            for (Shipment mergedShipment : mergedShipments) {
                shipmentMapper.updateOperate(
                        mergedShipment.getId(),
                        mergedShipment.getType(), mergedShipment.getSplitId(),
                        OperateType.MERGED.getNo(), mergedId
                );

                if (!tradeId.equals(mergedShipment.getTradeId())) {
                    throw new WebServiceException(WebServiceException.SHIPMENT_MERGED_NOT_IN_SAME_TRADE, "Merged shipments aren't in the same trade.");
                }

                //
                quantity += mergedShipment.getQuantity();
            }

            //3. create the new shipment.
            Shipment shipment = new Shipment();

            //
            shipment.setId(mergedId);
            shipment.setTradeId(tradeId);

            shipment.setType(ShipmentType.MERGE.getNo());
            shipment.setSplitId(null);

            shipment.setOperateType(OperateType.NONE.getNo());
            shipment.setMergedId(null);

            shipment.setQuantity(quantity);

            //
            shipment.setCreateDate(new Date());

            //
            if (shipmentMapper.create(shipment) > 0) {
                returnValue = shipment;
            }
        }

        return returnValue;
    }
}
