package club.showx.interview.shipment.controller;

import club.showx.interview.shipment.service.ShipmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


public class AbstractController implements Serializable {
    //
    private static final Logger logger = Logger.getLogger(AbstractController.class);

    @Autowired
    protected ShipmentService shipmentService;

}
