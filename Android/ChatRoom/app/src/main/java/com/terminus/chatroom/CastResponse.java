package com.terminus.chatroom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

public class CastResponse<T>  {


    public  static <T> T cast(String text,Class<T> tClass){
        Gson gson = new GsonBuilder().create();
        try {

            T  o =  gson.fromJson(text,tClass);
           if(o != null){
               return (T) o;
           }else {
               return null;
           }
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }
}
