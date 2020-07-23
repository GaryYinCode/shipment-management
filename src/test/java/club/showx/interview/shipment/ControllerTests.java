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
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@FixMethodOrder(MethodSorters.JVM)
class ControllerTests {
    @Autowired
    private RestTemplate testRestTemplate;

    @Test
    public void postTrade() {
        Map<String, String> multiValueMap = new HashMap<>();
        multiValueMap.put("title", "JUnit MockMvc create Trade");
        multiValueMap.put("quantity", "600");

        ModelJsonResponse response = testRestTemplate.postForObject("http://localhost:8080/api/trade/post?title={title}&quantity={quantity}", null, ModelJsonResponse.class, multiValueMap);

        Assert.assertTrue("Post Trade failed.", response.getCode() == 1);
    }

    //todo add more test cases
}
