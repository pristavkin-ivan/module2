package com.epam.esm.gift_certificate.entity;

import java.util.Objects;

public final class GiftCertificate implements Entity {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer duration;

    private String createDate;

    private String lastUpdateDate;

    public GiftCertificate() {
    }

    public GiftCertificate(Integer id, String name, String description, Double price, Integer duration
            , String createDate, String lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(id, that.id) && Objects.equals(duration, that.duration)
                && Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.equals(price, that.price) && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, name, description, price, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                '}';
    }

    public Integer getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
