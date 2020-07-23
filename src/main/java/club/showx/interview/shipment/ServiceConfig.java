package club.showx.interview.shipment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
    //
    @Value(value = "${server.port}")
    private String servicePort;
    @Value(value = "${spring.application.name}")
    private String serviceName;
}
