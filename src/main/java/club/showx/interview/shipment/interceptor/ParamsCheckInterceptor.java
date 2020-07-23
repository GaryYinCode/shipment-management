/**
 * CopyRight Fivewh.com 2013.
 */

package club.showx.interview.shipment.interceptor;

import club.showx.interview.shipment.ServiceConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Description:
 */
@Component
public class ParamsCheckInterceptor implements HandlerInterceptor {
    //
    private static final Logger logger = Logger.getLogger(ParamsCheckInterceptor.class);

    @Autowired
    private ServiceConfig serviceConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. log the request info.
        logRequest(request);

        //add more process logic here. such as client data parsing, auth checking, etc.

        //
        return true;
    }

    private void logRequest(HttpServletRequest request) {
        //
        if (logger.isInfoEnabled()) {
            //the url.
            StringBuilder stringBuilder = new StringBuilder();

            //
            stringBuilder.append(request.getMethod()).append(" ");
            if (request.getRequestURI() != null) {
                stringBuilder.append(request.getRequestURI());
            }
            if (request.getQueryString() != null) {
                stringBuilder.append("?").append(request.getQueryString());
            }

            //the header values.
            for (Enumeration<String> enumeration = request.getHeaderNames(); enumeration.hasMoreElements(); ) {
                String headerName = enumeration.nextElement();

                if (headerName.startsWith("x-")) {
                    stringBuilder.append(" -H").append(headerName).append("=").append(request.getHeader(headerName));
                }
            }

            //
            logger.info(stringBuilder.toString());
        }
    }
}
