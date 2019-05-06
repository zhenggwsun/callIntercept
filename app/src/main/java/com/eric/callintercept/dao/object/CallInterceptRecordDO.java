package com.eric.callintercept.dao.object;

import java.util.Date;

/**
 * CallInterceptRecordDO
 */
public class CallInterceptRecordDO {

    private Integer id;

    private String phoneNumber;

    private Date gmtCreate = new Date();

    public CallInterceptRecordDO() {
    }

    public CallInterceptRecordDO(Integer id, String phoneNumber, Date gmtCreate) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.gmtCreate = gmtCreate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
