package club.showx.interview.shipment.controller;

import club.showx.interview.shipment.WebServiceException;
import club.showx.interview.shipment.entity.Shipment;
import club.showx.interview.shipment.entity.Trade;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/trade")
public class TradeController extends AbstractController {


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ApiOperation(value = "Post a trade.")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "title", value = "The title of trade", defaultValue = "Sample Title", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "quantity", value = "The goods quantity of the trade", defaultValue = "123", required = true)
    })
    public ModelJsonResponse<Trade> postTrade(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "title") String title,
                                              @RequestParam(value = "quantity") int quantity) throws WebServiceException {
        //
        ModelJsonResponse<Trade> returnValue = new ModelJsonResponse<Trade>();

        //
        Trade trade = new Trade();

        trade.setCreateDate(new Date());
        //for example
        trade.setCreateUserId("user001");
        trade.setCreateIp(request.getRemoteAddr());

        trade.setTitle(title);
        trade.setQuantity(quantity);

        //
        returnValue.setResult(shipmentService.createTrade(trade));

        //
        return returnValue;
    }

    @RequestMapping(value = "/query/all", method = RequestMethod.GET)
    @ApiOperation(value = "Query all trades.")
    @ApiImplicitParams({
    })
    public ModelJsonResponse<List<Trade>> queryTradeAll(HttpServletRequest request, HttpServletResponse response) throws WebServiceException {
        //
        ModelJsonResponse<List<Trade>> returnValue = new ModelJsonResponse<List<Trade>>();

        returnValue.setResult(shipmentService.queryTradeAll());

        //
        return returnValue;
    }

    @RequestMapping(value = "/shipments", method = RequestMethod.GET)
    @ApiOperation(value = "Query all the shipment of a trade.")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "tradeid", value = "The trade id", defaultValue = "xyz-abc-efg", required = true)
    })
    public ModelJsonResponse<List<Shipment>> tradeShipments(HttpServletRequest request, HttpServletResponse response,
                                                            @RequestParam(value = "tradeid") String tradeId) throws WebServiceException {
        //
        ModelJsonResponse<List<Shipment>> returnValue = new ModelJsonResponse<List<Shipment>>();

        returnValue.setResult(shipmentService.queryShipmentsByTrade(tradeId));

        //
        return returnValue;
    }


    @RequestMapping(value = "/quantity/change", method = RequestMethod.POST)
    @ApiOperation(value = "Change the trade quantity.")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "tradeid", value = "The trade id", defaultValue = "xyz-abc-efg", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "quantity", value = "The new quantity", required = true)
    })
    public ModelJsonResponse<Boolean> changeQuantity(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(value = "tradeid") String tradeId,
                                                     @RequestParam(value = "quantity") int quantity) throws WebServiceException {
        //
        ModelJsonResponse<Boolean> returnValue = new ModelJsonResponse<Boolean>();

        returnValue.setResult(shipmentService.changeTradeQuantity(tradeId, quantity));

        //
        return returnValue;
    }
}
