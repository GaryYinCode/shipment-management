package club.showx.interview.shipment.model;

import java.io.Serializable;

/**
 * Description:
 *
 * @author <a mailto="yinpengyi@gmail.com">Yin Pengyi</a>
 */
public class ModelJsonResponse<RT> implements Serializable {
    //success.
    public static final int CODE_SUCCESS = 1;

    //error.
    public static final int CODE_ERROR = 0;

    //wrong.
    public static final int CODE_FAILED_WRONG_PARAM = -11;
    public static final int CODE_FAILED_ACCESS_DENY = -12;

    //
    private int code = ModelJsonResponse.CODE_ERROR;

    private int errorNo = 0;
    private String errorDesc = null;

    private RT result = null;

    //
    public ModelJsonResponse() {
        this(CODE_SUCCESS, 0, null, null);
    }

    public ModelJsonResponse(RT result) {
        this(CODE_SUCCESS, 0, null, result);
    }

    public ModelJsonResponse(int code) {
        this(code, 0, null, null);
    }

    public ModelJsonResponse(int code, int errorNo, String errorDesc) {
        this(code, errorNo, errorDesc, null);
    }

    public ModelJsonResponse(int code, int errorNo, String errorDesc, RT result) {
        this.code = code;

        this.errorNo = errorNo;
        this.errorDesc = errorDesc;

        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public RT getResult() {
        return result;
    }

    public void setResult(RT result) {
        this.result = result;
    }

    //
    public static <RT> ModelJsonResponse<RT> success(RT result) {
        return new ModelJsonResponse<RT>(result);
    }

    public static <RT> ModelJsonResponse<RT> error() {
        return new ModelJsonResponse<RT>(CODE_ERROR);
    }

    public static <RT> ModelJsonResponse<RT> error(int errorNo, String errorDesc) {
        ModelJsonResponse<RT> returnValue = new ModelJsonResponse<RT>(CODE_ERROR);
        returnValue.setErrorNo(errorNo);
        returnValue.setErrorDesc(errorDesc);

        //
        return returnValue;
    }
}
