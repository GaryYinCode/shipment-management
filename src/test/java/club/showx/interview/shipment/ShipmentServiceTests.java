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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagementApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ShipmentServiceTests {
    private static final Logger logger = Logger.getLogger(ShipmentServiceTests.class);

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

            Assert.assertTrue("the created trade is null.", trade != null);

            //
            if (trade != null) {
                tradeId = trade.getId();
            }
        } catch (Exception e) {
            logger.error("Post trade error.", e);
            Assert.assertTrue("create trade failed.", false);
        }
    }

    @Test
    void stepBQueryAllTrades() {
        try {
            List<Trade> trades = shipmentService.queryTradeAll();

            //
            Assert.assertTrue("Query all trades failed.", trades != null && trades.size() > 0);
        } catch (Exception e) {
            logger.error("Query all trades error.", e);
            Assert.assertTrue("Query all trades failed.", false);
        }
    }

    @Test
    void stepBGetTradeById() {
        try {
            Trade trade = shipmentService.getTradeById(tradeId);

            //
            Assert.assertTrue("Get trade by id failed.", trade != null);
        } catch (Exception e) {
            logger.error("Get trade by id error.", e);
            Assert.assertTrue("Get trade by id failed.", false);
        }
    }

    @Test
    void stepBQueryTradeShipments() {
        try {
            List<Shipment> shipments = shipmentService.queryShipmentsByTrade(tradeId);

            //
            Assert.assertTrue("Query shipments in a trade failed.", shipments != null && shipments.size() > 0);
        } catch (Exception e) {
            logger.error("Query shipments in trade error.", e);
            Assert.assertTrue("Query shipments in a trade failed.", false);
        }
    }


    @Test
    void stepCChangeTradeQuantity() {
        try {
            boolean changed = shipmentService.changeTradeQuantity(tradeId, 1200);

            //
            Assert.assertTrue("Change trade quantity failed.", changed);

            //
            if (changed) {
                Trade trade = shipmentService.getTradeById(tradeId);

                Assert.assertTrue("The trade quantity changing is failed.", trade.getQuantity() == 1200);
            }

            //
            changed = shipmentService.changeTradeQuantity(tradeId, 600);

            //
            Assert.assertTrue("Change trade quantity failed.", changed);

            //
            if (changed) {
                Trade trade = shipmentService.getTradeById(tradeId);

                Assert.assertTrue("The trade quantity changing is failed.", trade.getQuantity() == 600);
            }
        } catch (Exception e) {
            logger.error("Change trade quantity error.", e);
            Assert.assertTrue("change trade quantity failed.", false);
        }
    }

    @Test
    void stepDSplitMergeShipment() {
        try {
            List<Shipment> shipments = shipmentService.queryShipmentsByTrade(tradeId);
            for (Shipment shipment : shipments) {
                List<Integer> quantities = new ArrayList<Integer>();
                quantities.add(100);
                quantities.add(200);
                quantities.add(300);

                //
                List<Shipment> splitShipments = shipmentService.splitShipment(shipment.getId(), quantities);

                Assert.assertTrue("split shipment failed.", splitShipments != null && splitShipments.size() == 3);

                //
                List<String> mergedIds = new ArrayList<String>();
                int idx = 0;

                for (Shipment splitShipment : splitShipments) {
                    mergedIds.add(splitShipment.getId());

                    idx++;

                    if (idx >= 2) {
                        break;
                    }
                }

                Shipment mergedShipment = shipmentService.mergeShipments(tradeId, mergedIds);

                Assert.assertTrue("merge shipment failed.", mergedShipment != null);
            }
        } catch (Exception e) {
            logger.error("Split/merge shipment failed.", e);

            Assert.assertTrue("Split/merge shipment failed.", false);
        }
    }
}
