package club.showx.interview.shipment.controller;

import club.showx.interview.shipment.WebServiceException;
import club.showx.interview.shipment.entity.Shipment;
import club.showx.interview.shipment.model.ModelJsonResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/shipment")
public class ShipmentController extends AbstractController {

    @RequestMapping(value = "/split", method = RequestMethod.POST)
    @ApiOperation(value = "Split the shipment to multi shipments.")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "shipmentid", value = "The split shipment id", defaultValue = "xyz-abc-efg", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "quantities", value = "The split quantities", allowMultiple = true, required = true)
    })
    public ModelJsonResponse<List<Shipment>> splitShipment(HttpServletRequest request, HttpServletResponse response,
                                                           @RequestParam(value = "shipmentid") String shipmentId,
                                                           @RequestParam(value = "quantities") Collection<Integer> quantities) throws WebServiceException {
        //
        ModelJsonResponse<List<Shipment>> returnValue = new ModelJsonResponse<List<Shipment>>();

        //
        returnValue.setResult(shipmentService.splitShipment(shipmentId, quantities));

        //
        return returnValue;
    }

    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    @ApiOperation(value = "Split the shipment to multi shipments.")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "tradeid", value = "The trade id", defaultValue = "xyz-abc-efg", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "shipmentids", value = "The shipment ids", allowMultiple = true, required = true)
    })
    public ModelJsonResponse<Shipment> mergeShipments(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(value = "tradeid") String tradeId,
                                                      @RequestParam(value = "shipmentids") Collection<String> shipmentIds) throws WebServiceException {
        //
        ModelJsonResponse<Shipment> returnValue = new ModelJsonResponse<Shipment>();

        returnValue.setResult(shipmentService.mergeShipments(tradeId, shipmentIds));

        //
        return returnValue;
    }
}
