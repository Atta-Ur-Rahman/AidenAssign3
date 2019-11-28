
package com.techease.asign3.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallpaperResponseModel {

    @SerializedName("data")
    @Expose
    private List<WallpaperDataModel> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<WallpaperDataModel> getData() {
        return data;
    }

    public void setData(List<WallpaperDataModel> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
