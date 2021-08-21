package com.example.gorocery.myOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerOrderFields {


    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("store_id")
    @Expose
    private String storeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("total_discount")
    @Expose
    private Integer totalDiscount;
    @SerializedName("order_discount")
    @Expose
    private Integer orderDiscount;
    @SerializedName("service_charge")
    @Expose
    private Integer serviceCharge;
    @SerializedName("paid_amount")
    @Expose
    private Integer paidAmount;
    @SerializedName("due_amount")
    @Expose
    private Integer dueAmount;
    @SerializedName("file_path")
    @Expose
    private Object filePath;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order_details_id")
    @Expose
    private String orderDetailsId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("supplier_rate")
    @Expose
    private Integer supplierRate;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("address")
    @Expose
    private String address;


    public CustomerOrderFields(){}

    public CustomerOrderFields(String orderId, String customerId, String storeId, String userId, String date, Integer totalAmount, String order, String details, Integer totalDiscount, Integer orderDiscount, Integer serviceCharge, Integer paidAmount, Integer dueAmount, Object filePath, Integer status, String orderDetailsId, String productId, String variantId, Integer quantity, Integer rate, Integer supplierRate, Integer totalPrice, Integer discount, Object latitude, Object longitude, String address) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.storeId = storeId;
        this.userId = userId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.order = order;
        this.details = details;
        this.totalDiscount = totalDiscount;
        this.orderDiscount = orderDiscount;
        this.serviceCharge = serviceCharge;
        this.paidAmount = paidAmount;
        this.dueAmount = dueAmount;
        this.filePath = filePath;
        this.status = status;
        this.orderDetailsId = orderDetailsId;
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.rate = rate;
        this.supplierRate = supplierRate;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Integer totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Integer getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(Integer orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public Integer getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Integer serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Integer paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Integer dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Object getFilePath() {
        return filePath;
    }

    public void setFilePath(Object filePath) {
        this.filePath = filePath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getSupplierRate() {
        return supplierRate;
    }

    public void setSupplierRate(Integer supplierRate) {
        this.supplierRate = supplierRate;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
