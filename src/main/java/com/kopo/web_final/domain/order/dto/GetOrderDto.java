package com.kopo.web_final.domain.order.dto;

import java.time.LocalDate;

public class GetOrderDto {
    private String idOrder;
    private String noUser;
    private int qtOrderAmount;
    private int qtDeliMoney;
    private int qtDeliPeriod;
    private String nmOrderPerson;
    private String nmReceiver;
    private String noDeliveryZipno;
    private String nmDeliveryAddress;
    private String nmReceiverTelno;
    private String nmDeliverySpace;
    private String cdOrderType;
    private LocalDate daOrder;
    private String stOrder;
    private String stPayment;
    private String noRegister;

    public GetOrderDto() {
    }

    public GetOrderDto(String idOrder, String noUser, int qtOrderAmount, int qtDeliMoney, int qtDeliPeriod,
                       String nmOrderPerson, String nmReceiver, String noDeliveryZipno, String nmDeliveryAddress,
                       String nmReceiverTelno, String nmDeliverySpace, String cdOrderType, LocalDate daOrder,
                       String stOrder, String stPayment, String noRegister) {
        this.idOrder = idOrder;
        this.noUser = noUser;
        this.qtOrderAmount = qtOrderAmount;
        this.qtDeliMoney = qtDeliMoney;
        this.qtDeliPeriod = qtDeliPeriod;
        this.nmOrderPerson = nmOrderPerson;
        this.nmReceiver = nmReceiver;
        this.noDeliveryZipno = noDeliveryZipno;
        this.nmDeliveryAddress = nmDeliveryAddress;
        this.nmReceiverTelno = nmReceiverTelno;
        this.nmDeliverySpace = nmDeliverySpace;
        this.cdOrderType = cdOrderType;
        this.daOrder = daOrder;
        this.stOrder = stOrder;
        this.stPayment = stPayment;
        this.noRegister = noRegister;
    }

    // Getters and Setters

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getNoUser() {
        return noUser;
    }

    public void setNoUser(String noUser) {
        this.noUser = noUser;
    }

    public int getQtOrderAmount() {
        return qtOrderAmount;
    }

    public void setQtOrderAmount(int qtOrderAmount) {
        this.qtOrderAmount = qtOrderAmount;
    }

    public int getQtDeliMoney() {
        return qtDeliMoney;
    }

    public void setQtDeliMoney(int qtDeliMoney) {
        this.qtDeliMoney = qtDeliMoney;
    }

    public int getQtDeliPeriod() {
        return qtDeliPeriod;
    }

    public void setQtDeliPeriod(int qtDeliPeriod) {
        this.qtDeliPeriod = qtDeliPeriod;
    }

    public String getNmOrderPerson() {
        return nmOrderPerson;
    }

    public void setNmOrderPerson(String nmOrderPerson) {
        this.nmOrderPerson = nmOrderPerson;
    }

    public String getNmReceiver() {
        return nmReceiver;
    }

    public void setNmReceiver(String nmReceiver) {
        this.nmReceiver = nmReceiver;
    }

    public String getNoDeliveryZipno() {
        return noDeliveryZipno;
    }

    public void setNoDeliveryZipno(String noDeliveryZipno) {
        this.noDeliveryZipno = noDeliveryZipno;
    }

    public String getNmDeliveryAddress() {
        return nmDeliveryAddress;
    }

    public void setNmDeliveryAddress(String nmDeliveryAddress) {
        this.nmDeliveryAddress = nmDeliveryAddress;
    }

    public String getNmReceiverTelno() {
        return nmReceiverTelno;
    }

    public void setNmReceiverTelno(String nmReceiverTelno) {
        this.nmReceiverTelno = nmReceiverTelno;
    }

    public String getNmDeliverySpace() {
        return nmDeliverySpace;
    }

    public void setNmDeliverySpace(String nmDeliverySpace) {
        this.nmDeliverySpace = nmDeliverySpace;
    }

    public String getCdOrderType() {
        return cdOrderType;
    }

    public void setCdOrderType(String cdOrderType) {
        this.cdOrderType = cdOrderType;
    }

    public LocalDate getDaOrder() {
        return daOrder;
    }

    public void setDaOrder(LocalDate daOrder) {
        this.daOrder = daOrder;
    }

    public String getStOrder() {
        return stOrder;
    }

    public void setStOrder(String stOrder) {
        this.stOrder = stOrder;
    }

    public String getStPayment() {
        return stPayment;
    }

    public void setStPayment(String stPayment) {
        this.stPayment = stPayment;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }
}
