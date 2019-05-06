package com.eric.callintercept.dao.object;

import java.util.Date;

/**
 * CallInterceptRecordDO
 */
public class MessageInterceptRecordDO {

    private String phoneNumber;

    private Date gmtCreate = new Date();

    private String content;

    public MessageInterceptRecordDO() {
    }

    public MessageInterceptRecordDO(String phoneNumber, Date gmtCreate, String content) {
        this.phoneNumber = phoneNumber;
        this.gmtCreate = gmtCreate;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
