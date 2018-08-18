package com.yahoo.r4hu7.moviesdoughnut.data.local.entity;

import android.arch.persistence.room.TypeConverter;

import com.google.common.primitives.Ints;
import com.google.gson.Gson;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.MovieImagesResponse;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Item;

public class MovieTypeConverter {

    @TypeConverter
    public static String intsToString(int[] ints) {
        return Ints.join(",", ints);
    }

    @TypeConverter
    public static int[] stringToInts(String s) {
        String[] strings = s.split(",");
        int[] ints = new int[strings.length];
        for (int i = 0; i < s.length(); i++) {
            ints[i] = Integer.parseInt(s);
        }
        return ints;
    }

    @TypeConverter
    public static String itemsToString(Item[] items) {
        return new Gson().toJson(items);
    }

    @TypeConverter
    public static Item[] stringToItems(String s) {
        return new Gson().fromJson(s, Item[].class);
    }

    @TypeConverter
    public static String imageRespToString(MovieImagesResponse response) {
        return new Gson().toJson(response);
    }

    @TypeConverter
    public static MovieImagesResponse stringToImageResp(String s) {
        return new Gson().fromJson(s, MovieImagesResponse.class);
    }

}
