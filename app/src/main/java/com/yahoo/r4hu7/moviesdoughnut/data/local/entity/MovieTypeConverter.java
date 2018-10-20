package com.yahoo.r4hu7.moviesdoughnut.data.local.entity;

import android.arch.persistence.room.TypeConverter;

import com.google.common.primitives.Ints;
import com.google.gson.Gson;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Backdrop;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Cast;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Crew;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Item;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Poster;
import com.yahoo.r4hu7.moviesdoughnut.data.remote.response.model.Video;

public class MovieTypeConverter {

    @TypeConverter
    public static String intsToString(int[] ints) {
        return Ints.join(",", ints);
    }

    @TypeConverter
    public static int[] stringToInts(String s) {
        String[] strings = s.trim().split(",");
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            try {
                ints[i] = Integer.parseInt(strings[i]);
            } catch (NumberFormatException ignored) {
            }
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
    public static String backdropToString(Backdrop[] backdrops) {
        return objToStr(backdrops);
    }

    @TypeConverter
    public static Backdrop[] stringToBackdrops(String s) {
        return strToObj(s, Backdrop[].class);
    }

    @TypeConverter
    public static String postersToString(Poster[] posters) {
        return objToStr(posters);
    }

    @TypeConverter
    public static Poster[] stringToPosters(String s) {
        return strToObj(s, Poster[].class);
    }

    @TypeConverter
    public static String videosToString(Video[] videos) {
        return new Gson().toJson(videos);
    }

    @TypeConverter
    public static Video[] stringToVideos(String s) {
        return new Gson().fromJson(s, Video[].class);
    }

    @TypeConverter
    public static String castsToString(Cast[] casts) {
        return objToStr(casts);
    }

    @TypeConverter
    public static Cast[] stringToCasts(String s) {
        return strToObj(s, Cast[].class);
    }

    @TypeConverter
    public static String crewsToString(Crew[] crews) {
        return objToStr(crews);
    }

    @TypeConverter
    public static Crew[] stringToCrews(String s) {
        return strToObj(s, Crew[].class);
    }

    private static <T> String objToStr(T t) {
        return new Gson().toJson(t);
    }

    private static <T> T strToObj(String s, Class<T> classOfT) {
        return new Gson().fromJson(s, classOfT);
    }

}
