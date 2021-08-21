package com.example.gorocery.grocery;

public class GroceryItemsFields {

    private String cName,cId,cImgUrl,topMenue,menuPos,catFavIcon;

    public GroceryItemsFields(String cName, String cId, String cImgUrl, String topMenue, String menuPos, String catFavIcon) {
        this.cName = cName;
        this.cId = cId;
        this.cImgUrl = cImgUrl;
        this.topMenue = topMenue;
        this.menuPos = menuPos;
        this.catFavIcon = catFavIcon;
    }

    public GroceryItemsFields(){}

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcImgUrl() {
        return cImgUrl;
    }

    public void setcImgUrl(String cImgUrl) {
        this.cImgUrl = cImgUrl;
    }

    public String getTopMenue() {
        return topMenue;
    }

    public void setTopMenue(String topMenue) {
        this.topMenue = topMenue;
    }

    public String getMenuPos() {
        return menuPos;
    }

    public void setMenuPos(String menuPos) {
        this.menuPos = menuPos;
    }

    public String getCatFavIcon() {
        return catFavIcon;
    }

    public void setCatFavIcon(String catFavIcon) {
        this.catFavIcon = catFavIcon;
    }
}
