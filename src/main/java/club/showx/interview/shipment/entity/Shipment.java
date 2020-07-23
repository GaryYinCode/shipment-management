package club.showx.interview.shipment.entity;

import java.io.Serializable;
import java.util.Date;

public class Shipment implements Serializable {
    //
    private String id;

    //
    private String tradeId;

    // 1=root,2=split,3=merge
    private int type = 1;
    // the id which the shipment was split from
    private String splitId;

    // 0=none, 1=split, 2=merged
    private int operateType = 0;
    // the id which the shipment was merged to
    private String mergedId;

    //
    private int quantity;

    //
    private Date createDate;
    private String createUserId;
    private String createIp;

    //
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getMergedId() {
        return mergedId;
    }

    public void setMergedId(String mergedId) {
        this.mergedId = mergedId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }
}
