package com.example.gorocery.GroceryDetails;

public class GrocerySubCatFields {

    private String cId,cParentId,cName,cTopMenu,cMenuPos,cImgUrl,cFavicon,cCatType,cStatus;


    public GrocerySubCatFields(){}

    public GrocerySubCatFields(String cId, String cParentId, String cName, String cTopMenu, String cMenuPos, String cImgUrl, String cFavicon, String cCatType, String cStatus) {
        this.cId = cId;
        this.cParentId = cParentId;
        this.cName = cName;
        this.cTopMenu = cTopMenu;
        this.cMenuPos = cMenuPos;
        this.cImgUrl = cImgUrl;
        this.cFavicon = cFavicon;
        this.cCatType = cCatType;
        this.cStatus = cStatus;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcParentId() {
        return cParentId;
    }

    public void setcParentId(String cParentId) {
        this.cParentId = cParentId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcTopMenu() {
        return cTopMenu;
    }

    public void setcTopMenu(String cTopMenu) {
        this.cTopMenu = cTopMenu;
    }

    public String getcMenuPos() {
        return cMenuPos;
    }

    public void setcMenuPos(String cMenuPos) {
        this.cMenuPos = cMenuPos;
    }

    public String getcImgUrl() {
        return cImgUrl;
    }

    public void setcImgUrl(String cImgUrl) {
        this.cImgUrl = cImgUrl;
    }

    public String getcFavicon() {
        return cFavicon;
    }

    public void setcFavicon(String cFavicon) {
        this.cFavicon = cFavicon;
    }

    public String getcCatType() {
        return cCatType;
    }

    public void setcCatType(String cCatType) {
        this.cCatType = cCatType;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }
}
