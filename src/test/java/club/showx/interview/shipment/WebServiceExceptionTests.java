package club.showx.interview.shipment;

import club.showx.interview.shipment.entity.Shipment;
import club.showx.interview.shipment.entity.Trade;
import club.showx.interview.shipment.service.ShipmentService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementApplication.class)
class WebServiceExceptionTests {
    private static final Logger logger = Logger.getLogger(WebServiceExceptionTests.class);

    @Autowired
    private ShipmentService shipmentService;

    //
    private static String tradeId = null;

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
            Assert.assertTrue("Trade Not exist exception.", e.getNo() == WebServiceException.SHIPMENT_TRADE_NOT_EXIST);
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
}
