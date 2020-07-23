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
@SpringBootTest
@FixMethodOrder(MethodSorters.JVM)
class ShipmentServiceTests {
    private static final Logger logger = Logger.getLogger(ShipmentServiceTests.class);

    @Autowired
    private ShipmentService shipmentService;

    //
    private static String tradeId = null;

    @Test
    void createTrade() {
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
            Assert.assertTrue("create trade failed.", false);
        }
    }

    @Test
    void changeTradeQuantity() {
        try {
            boolean changed = shipmentService.changeTradeQuantity(tradeId, 1200);

            //
            Assert.assertTrue("Change trade quantity failed.", changed);

            //
            if (changed) {
                Trade trade = shipmentService.getTradeById(tradeId);

                Assert.assertTrue("The trade is not exist.", trade != null);
                Assert.assertTrue("The trade quantity changing is failed.", trade.getQuantity() == 1200);
            }

            shipmentService.changeTradeQuantity(tradeId, 600);
        } catch (Exception e) {
            Assert.assertTrue("change trade quantity failed.", false);
        }
    }

    @Test
    void splitShipment() {
        try {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%" + tradeId);
            List<Shipment> shipments = shipmentService.queryShipmentsByTrade(tradeId);
            for (Shipment shipment : shipments) {
                if (shipment.getTradeId().equals(tradeId)) {
                    List<Integer> quantities = new ArrayList<Integer>();
                    quantities.add(100);
                    quantities.add(200);
                    quantities.add(300);

                    //
                    List<String> mergedIds = new ArrayList<String>();

                    int idx = 0;
                    List<Shipment> splitShipments = shipmentService.splitShipment(shipment.getId(), quantities);

                    Assert.assertTrue("split shipment failed.", splitShipments != null && splitShipments.size() == 3);

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
            }
        } catch (Exception e) {
            logger.error("split/merge shipment failed.", e);

            Assert.assertTrue("split/merge shipment failed.", false);
        }
    }
}
