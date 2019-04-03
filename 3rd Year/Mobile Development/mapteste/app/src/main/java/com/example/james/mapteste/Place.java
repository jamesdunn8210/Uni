package com.example.james.mapteste;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by James on 04/03/2018.
 */

public class Place implements Parcelable {

    private String name;
    private double lat;
    private double lon;
    private LatLng position;


    public Place(){

    }

    public Place(String name, double lat, double lon){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    //parcel part
    public Place(Parcel in){
        String[]data = new String[3];

        in.readStringArray(data);
        this.name= data[0];
        this.lat = Double.parseDouble(data[1]);
        this.lon = Double.parseDouble(data[2]);

    }
    @Override
    public int describeContents() {
    return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name, String.valueOf(this.lat),String.valueOf(this.lon)});
    }

    public static final Parcelable.Creator<Place> CREATOR= new Parcelable.Creator<Place>() {

        @Override
        public Place createFromParcel(Parcel source) {
// TODO Auto-generated method stub
            return new Place(source);  //using parcelable constructor
        }

        @Override
        public Place[] newArray(int size) {
// TODO Auto-generated method stub
            return new Place[size];
        }
    };


    public void setName(String name) { this.name = name;}
    public void setLat(Double lat)   { this.lat = lat; }
    public void setLon(Double lon)   { this.lon = lon; }


    public String getName() { return name;}
    public Double getLat() { return lat; }
    public Double getLon() { return lon; }
    public LatLng getPosition() {return new LatLng(lat,lon);}


}

