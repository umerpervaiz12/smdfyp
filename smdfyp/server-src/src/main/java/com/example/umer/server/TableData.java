package com.example.umer.server;

/**
 * Created by Umer on 6/6/2015.
 */
import android.provider.BaseColumns;

/**
 * Created by Ammar on 6/4/2015.
 */
public class TableData {

    String _ph_no;
    String _details;
    String _lng;
    String _lat;

    // Empty constructor
    public TableData(){

    }
    // constructor
    public TableData(String ph_no, String details, String lng,String lat ){
        this._ph_no = ph_no;
        this._details = details;
        this._lng = lng;
        this._lat = lat;
    }

    // getting ID
    public String getphonenumber(){
        return this._ph_no;
    }

    // setting id
    public void setphonenumber(String ph_no){
        this._ph_no = ph_no;
    }

    // getting name
    public String getdetails(){
        return this._details;
    }

    // setting name
    public void setdetails(String details){
        this._details = details;
    }

    // getting phone number
    public String getlongitude(){
        return this._lng;
    }

    // setting phone number
    public void setlongitude(String lng){
        this._lng = lng;
    }

    public String getlatitude(){
        return this._lat;
    }

    // setting phone number
    public void setlatitude(String lat){
        this._lat = lat;
    }
}
