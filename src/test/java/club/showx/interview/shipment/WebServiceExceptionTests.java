package club.showx.interview.shipment;

import club.showx.interview.shipment.entity.Shipment;
import club.showx.interview.shipment.entity.Trade;
import club.showx.interview.shipment.service.ShipmentService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WebServiceExceptionTests {
    private static final Logger logger = Logger.getLogger(WebServiceExceptionTests.class);

    @Autowired
    private ShipmentService shipmentService;

    //
    private static String tradeId = null;
    private static List<String> mergedShipmentIds = new ArrayList<String>();

    @Test
    void stepAPostTrade() {
        try {
            Trade trade = new Trade();

            trade.setQuantity(600);
            trade.setTitle("JUnit created Trade");

            trade.setCreateDate(new Date());
            trade.setCreateUserId("user-1000");
            trade.setCreateIp("127.0.0.1");

            trade = shipmentService.createTrade(trade);

            //
            if (trade != null) {
                tradeId = trade.getId();

                List<Shipment> shipments = shipmentService.queryShipmentsByTrade(tradeId);
                for (Shipment shipment : shipments) {
                    mergedShipmentIds.add(shipment.getId());
                }
            }
        } catch (Exception e) {
            logger.error("postTrade error.", e);
            Assert.assertTrue("Post trade exception.", false);
        }
    }

    @Test
    void stepBTradeNotExistException() {
        try {
            shipmentService.queryShipmentsByTrade(tradeId + "-xyz");
        } catch (WebServiceException e) {
            logger.error("queryShipmentsByTrade error.", e);
            Assert.assertTrue("Trade doesn't exist exception.", e.getNo() == WebServiceException.SHIPMENT_TRADE_NOT_EXIST);
        }
    }

    @Test
    void stepBShipmentNotExistException() {
        try {
            shipmentService.splitShipment(UUID.randomUUID().toString(), null);
        } catch (WebServiceException e) {
            logger.error("splitShipment error.", e);
            Assert.assertTrue("Shipment doesn't exist exception.", e.getNo() == WebServiceException.SHIPMENT_NOT_EXIST);
        }
    }

    @Test
    void stepCShipmentSplitQuantityNotEqualException() {
        try {
            List<Shipment> shipments = shipmentService.queryShipmentsByTrade(tradeId);
            for (Shipment shipment : shipments) {
                List<Integer> quantities = new ArrayList<Integer>();
                quantities.add(100);
                quantities.add(200);
                quantities.add(200);

                //
                shipmentService.splitShipment(shipment.getId(), quantities);
            }
        } catch (WebServiceException e) {
            logger.error("splitShipment error.", e);
            Assert.assertTrue("Shipment split quantity is not equals to sum exception.", e.getNo() == WebServiceException.SHIPMENT_SPLIT_QUANTITY_NOT_EQUALS);
        }
    }

    @Test
    void stepFMergedSizeLessThan2() {
        try {
            List<String> mergedIds = new ArrayList<String>();
            mergedIds.add(UUID.randomUUID().toString());

            shipmentService.mergeShipments(tradeId, mergedIds);
        } catch (WebServiceException e) {
            logger.error("mergeShipment error.", e);
            Assert.assertTrue("Merged shipments size is less than 2.", e.getNo() == WebServiceException.SHIPMENT_MERGED_SIZE_LESS_THAN_2);
        }
    }

    @Test
    void stepIMergedShipmentNotInSameTrade() {
        try {
            mergedShipmentIds.add(UUID.randomUUID().toString());

            shipmentService.mergeShipments(tradeId + "-xyz", mergedShipmentIds);
        } catch (WebServiceException e) {
            logger.error("mergeShipment error.", e);
            Assert.assertTrue("Merged shipments don't in same trade.", e.getNo() == WebServiceException.SHIPMENT_MERGED_NOT_IN_SAME_TRADE);
        }
    }
}
