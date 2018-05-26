/*
 *
 *  * PROJECT LICENSE
 *  *
 *  * This project was submitted by Beatriz Ovejero as part of the Android Developer
 *  * Nanodegree at Udacity.
 *  *
 *  * As part of Udacity Honor code, your submissions must be your own work, hence
 *  * submitting this project as yours will cause you to break the Udacity Honor Code
 *  * and the suspension of your account.
 *  *
 *  * As author of the project, I allow you to check it as a reference, but if you submit it
 *  * as your own project, it's your own responsibility if you get expelled.
 *  *
 *  * Copyright (c) 2018 Beatriz Ovejero
 *  *
 *  * Besides the above notice, the following license applies and this license notice must be
 *  * included in all works derived from this project.
 *  *
 *  * MIT License
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

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