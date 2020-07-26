package club.showx.interview.shipment;

import club.showx.interview.shipment.model.ModelJsonResponse;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ControllerTests {
    @Autowired
    private RestTemplate restTemplate;

    //
    private static String tradeId;

    @Test
    public void stepAPostTrade() {
        Map<String, String> multiValueMap = new HashMap<>();
        multiValueMap.put("title", "JUnit MockMvc create Trade");
        multiValueMap.put("quantity", "600");

        ModelJsonResponse response = restTemplate.postForObject("http://localhost:8080/api/trade/post?title={title}&quantity={quantity}", null, ModelJsonResponse.class, multiValueMap);

        Assert.assertTrue("Post Trade failed.", response.getCode() == 1);

        tradeId = (String) ((LinkedHashMap) response.getResult()).get("id");
    }

    @Test
    public void stepBQueryAllTrade() {
        Map<String, String> multiValueMap = new HashMap<>();

        ModelJsonResponse response = restTemplate.getForObject("http://localhost:8080/api/trade/query/all", ModelJsonResponse.class, multiValueMap);

        Assert.assertTrue("Query all trades failed.", response.getCode() == 1);
    }

    @Test
    public void stepBQueryShipmentsInTrade() {
        Map<String, String> multiValueMap = new HashMap<>();
        multiValueMap.put("tradeId", tradeId);

        ModelJsonResponse response = restTemplate.getForObject("http://localhost:8080/api/trade/shipments?tradeid={tradeId}", ModelJsonResponse.class, multiValueMap);

        Assert.assertTrue("Query shipments in trade failed.", response.getCode() == 1);
    }

    @Test
    public void stepBChangeTradeQuantity() {
        Map<String, String> multiValueMap = new HashMap<>();
        multiValueMap.put("tradeId", tradeId);
        multiValueMap.put("quantity", "1200");

        ModelJsonResponse response = restTemplate.postForObject("http://localhost:8080/api/trade/quantity/change?tradeid={tradeId}&quantity={quantity}", null, ModelJsonResponse.class, multiValueMap);

        Assert.assertTrue("Change trade quantity failed.", response.getCode() == 1);

        //reset the quantity.
        multiValueMap.put("quantity", "600");
        response = restTemplate.postForObject("http://localhost:8080/api/trade/quantity/change?tradeid={tradeId}&quantity={quantity}", null, ModelJsonResponse.class, multiValueMap);

        Assert.assertTrue("Change trade quantity failed.", response.getCode() == 1);
    }
}
