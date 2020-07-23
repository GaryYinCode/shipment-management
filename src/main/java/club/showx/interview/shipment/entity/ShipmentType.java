package club.showx.interview.shipment.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShipmentType implements Serializable {
    //
    private static Map<Integer, ShipmentType> nosMap = new LinkedHashMap<Integer, ShipmentType>();
    private static Map<String, ShipmentType> codesMap = new LinkedHashMap<String, ShipmentType>();

    //
    public final static ShipmentType ROOT = new ShipmentType(1, "root");
    public final static ShipmentType SPLIT = new ShipmentType(2, "split");
    public final static ShipmentType MERGE = new ShipmentType(3, "merge");

    //
    private int no;
    private String code;

    //
    private ShipmentType(int no, String code) {
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

    public static ShipmentType getByNo(int id) {
        return nosMap.get(id);
    }

    public static ShipmentType getByCode(String code) {
        return code == null ? null : codesMap.get(code.toLowerCase());
    }

    public static Collection<ShipmentType> allValues() {
        return codesMap.values();
    }

    @Override
    public int hashCode() {
        return no;
    }

    @Override
    public String toString() {
        return "ShipmentType [no: " + no + ", code: " + code + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ShipmentType && no == ((ShipmentType) obj).getNo();
    }
}