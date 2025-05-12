package com.kopo.web_final.order.model;

import java.time.LocalDate;

public class Order {
    private String idOrder;              // 주문 ID (PK)
    private String noUser;               // 사용자 번호 (FK)

    private Integer qtOrderAmount;       // 주문 총 금액
    private Integer qtDeliMoney;         // 배송비
    private Integer qtDeliPeriod;        // 배송 기간

    private String nmOrderPerson;        // 주문자 이름
    private String nmReceiver;           // 수령인 이름

    private String noDeliveryZipno;      // 우편번호
    private String nmDeliveryAddress;    // 주소
    private String nmReceiverTelno;      // 수령인 연락처
    private String nmDeliverySpace;      // 배송 요청사항

    private String cdOrderType;          // 주문 유형 코드
    private LocalDate daOrder;                // 주문일자

    private String stOrder;              // 주문 상태
    private String stPayment;            // 결제 상태

    private String noRegister;           // 등록자 ID
    private LocalDate daFirstDate;            // 등록일자

    public Order() {
    }

    public Order(String idOrder, String noUser, Integer qtOrderAmount, Integer qtDeliMoney, Integer qtDeliPeriod, String nmOrderPerson, String nmReceiver, String noDeliveryZipno, String nmDeliveryAddress, String nmReceiverTelno, String nmDeliverySpace, String cdOrderType, LocalDate daOrder, String stOrder, String stPayment, String noRegister, LocalDate daFirstDate) {
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
        this.daFirstDate = daFirstDate;
    }

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

    public Integer getQtOrderAmount() {
        return qtOrderAmount;
    }

    public void setQtOrderAmount(Integer qtOrderAmount) {
        this.qtOrderAmount = qtOrderAmount;
    }

    public Integer getQtDeliMoney() {
        return qtDeliMoney;
    }

    public void setQtDeliMoney(Integer qtDeliMoney) {
        this.qtDeliMoney = qtDeliMoney;
    }

    public Integer getQtDeliPeriod() {
        return qtDeliPeriod;
    }

    public void setQtDeliPeriod(Integer qtDeliPeriod) {
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

    public LocalDate getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(LocalDate daFirstDate) {
        this.daFirstDate = daFirstDate;
    }
}
