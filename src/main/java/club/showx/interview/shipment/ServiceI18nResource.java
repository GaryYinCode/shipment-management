package club.showx.interview.shipment;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ServiceI18nResource {
    //
    private static final Logger logger = Logger.getLogger(ServiceI18nResource.class);

    //
    private static final String KEY_DEFAULT_WEBSERVICE_EXCEPTION = "webservice.exception.0.name";

    @Autowired
    private MessageSource messageSource;

    public String get(String fullKey) {
        String returnValue = fullKey;

        try {
            return messageSource.getMessage(fullKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            logger.error("get error.", e);
        }

        return returnValue;
    }

    public String getWebServiceException(int exceptionNo) {
        String returnValue = null;

        //
        String key = "webservice.exception." + exceptionNo + ".name";
        try {
            returnValue = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
            if (returnValue == null || returnValue.equals("")) {
                returnValue = messageSource.getMessage(KEY_DEFAULT_WEBSERVICE_EXCEPTION, null, LocaleContextHolder.getLocale());
            }
        } catch (Exception e) {
            logger.error("getWebServiceException error.", e);
        }

        if (returnValue == null || returnValue.equals("")) {
            returnValue = key;
        }

        return returnValue;
    }
}
