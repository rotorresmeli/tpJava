package entities;

import java.util.Arrays;

public class Item implements Comparable{
    private String id;
    private String siteId;
    private String title;
    private int price;
    private Currency currency;
    private String listingType;
    private String stopTime;
    private String thumbnail;
    private String[] tags;

    public enum OrderElement {
        PRICE,
        LISTING_TYPE
    }

    public static OrderElement orderElement = OrderElement.PRICE;

    public Item() {

    }
    public Item(String id, String siteId, String title, int price, Currency currency, String listingType, String stopTime, String thumbnail, String[] tags) {
        this.id = id;
        this.siteId = siteId;
        this.title = title;
        this.price = price;
        this.currency = currency;
        this.listingType = listingType;
        this.stopTime = stopTime;
        this.thumbnail = thumbnail;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public static OrderElement getOrderElement() {
        return orderElement;
    }

    public static void setOrderElement(OrderElement orderElement) {
        Item.orderElement = orderElement;
    }

    @Override
    public String toString() {
        return "entities.Item{" +
                "id='" + id + '\'' +
                ", siteId='" + siteId + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", listingType=" + listingType +
                ", stopTime=" + stopTime +
                ", thumbnail='" + thumbnail + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;
        Item item = (Item) o;
        switch(orderElement) {
            case PRICE:
                if (this.getPrice() < item.getPrice()) {
                    return -1;
                } else if (this.getPrice() == item.getPrice()) {
                    return 0;
                } else {
                    return 1;
                }
            case LISTING_TYPE:
                result = this.listingType.compareTo(item.listingType);
        }
        return result;
    }

}
