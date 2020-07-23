package club.showx.interview.shipment.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class OperateType implements Serializable {
    //
    private static Map<Integer, OperateType> nosMap = new LinkedHashMap<Integer, OperateType>();
    private static Map<String, OperateType> codesMap = new LinkedHashMap<String, OperateType>();

    //
    public final static OperateType NONE = new OperateType(0, "none");
    public final static OperateType SPLIT = new OperateType(1, "split");
    public final static OperateType MERGED = new OperateType(2, "merged");

    //
    private int no;
    private String code;

    //
    private OperateType(int no, String code) {
        this.no = no;
        this.code = code.toLowerCase();

        //
        nosMap.put(this.no, this);
        codesMap.put(this.code, this);
    }

    public int getNo() {
        return no;
    }

    public String getCode() {
        return code;
    }

    public static OperateType getByNo(int id) {
        return nosMap.get(id);
    }

    public static OperateType getByCode(String code) {
        return code == null ? null : codesMap.get(code.toLowerCase());
    }

    public static Collection<OperateType> allValues() {
        return codesMap.values();
    }

    @Override
    public int hashCode() {
        return no;
    }

    @Override
    public String toString() {
        return "OperateType [no: " + no + ", code: " + code + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof OperateType && no == ((OperateType) obj).getNo();
    }
}