package club.showx.interview.shipment.controller;

import club.showx.interview.shipment.ServiceI18nResource;
import club.showx.interview.shipment.WebServiceException;
import club.showx.interview.shipment.model.ModelJsonResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ServiceExceptionHandler {
    private static final Logger logger = Logger.getLogger(ServiceExceptionHandler.class);

    @Autowired
    protected ServiceI18nResource i18nResource;

    @ExceptionHandler(value = Exception.class)
    public ModelJsonResponse defaultExceptionHandler(Exception e, HttpServletResponse response) {
        logger.error("defaultExceptionHandler reports exception.", e);

        return ModelJsonResponse.error(WebServiceException.GENERIC, i18nResource.getWebServiceException(WebServiceException.GENERIC));
    }

    @ExceptionHandler(value = WebServiceException.class)
    public ModelJsonResponse webServiceExceptionHandler(WebServiceException e, HttpServletResponse response) {
        logger.error("WebServiceExceptionHandler reports exception.", e);

        return ModelJsonResponse.error(e.getNo(), i18nResource.getWebServiceException(e.getNo()));
    }
}
