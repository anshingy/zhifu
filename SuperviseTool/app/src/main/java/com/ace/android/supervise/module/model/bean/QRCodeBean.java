package com.ace.android.supervise.module.model.bean;

import java.io.Serializable;

/**
 * Created by ace on 2018/10/23
 */

public class QRCodeBean implements Serializable {

    private String amount;
    private String associatedCode;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAssociatedCode() {
        return associatedCode;
    }

    public void setAssociatedCode(String associatedCode) {
        this.associatedCode = associatedCode;
    }
}
