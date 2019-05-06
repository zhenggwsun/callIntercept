package com.eric.callintercept.dao.object;

/**
 * phone_number DO
 */
public class PhoneNumberDO {

    private Integer id;

    private String phoneNumber;

    private String type;

    public PhoneNumberDO() {
    }

    public PhoneNumberDO(Integer id, String phoneNumber, String type) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
