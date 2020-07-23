package club.showx.interview.shipment.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {
    @Autowired
    private ParamsCheckInterceptor paramsCheckInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //params check interceptors
        registry.addInterceptor(paramsCheckInterceptor).
                addPathPatterns("/**");

        //add more interceptors here.
    }
}
