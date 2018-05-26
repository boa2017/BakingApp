package com.example.android.bakingapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by beita on 16/05/2018.
 */

public class Steps implements Parcelable {
    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
    @JsonProperty("videoURL")
    private String videoURL;
    @JsonProperty("description")
    private String description;
    @JsonProperty("id")
    private int id;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("thumbnailURL")
    private String thumbnailURL;

    public Steps() {
        this.videoURL = "";
        this.description = "";
        this.id = 0;
        this.shortDescription = "";
        this.thumbnailURL = "";
    }

    protected Steps(Parcel in) {
        this.videoURL = in.readString();
        this.description = in.readString();
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.thumbnailURL = in.readString();
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.videoURL);
        parcel.writeString(this.description);
        parcel.writeInt(this.id);
        parcel.writeString(this.shortDescription);
        parcel.writeString(this.thumbnailURL);
    }

    // Getters
    public String getVideoURL() {
        return videoURL;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "videoURL='" + videoURL + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}