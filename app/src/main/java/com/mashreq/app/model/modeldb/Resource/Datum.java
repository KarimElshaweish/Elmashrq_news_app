package com.mashreq.app.model.modeldb.Resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("cover_photo")
    @Expose
    private String coverPhoto;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("number_of_follow")
    @Expose
    private Integer numberOfFollow;
    @SerializedName("is_follow")
    @Expose
    private Boolean isFollow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getNumberOfFollow() {
        return numberOfFollow;
    }

    public void setNumberOfFollow(Integer numberOfFollow) {
        this.numberOfFollow = numberOfFollow;
    }

    public Boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Boolean isFollow) {
        this.isFollow = isFollow;
    }

}
