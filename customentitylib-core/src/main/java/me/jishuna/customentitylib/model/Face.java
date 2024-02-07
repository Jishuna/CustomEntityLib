package me.jishuna.customentitylib.model;

import com.google.gson.annotations.SerializedName;

public enum Face {
    @SerializedName("north")
    NORTH,
    @SerializedName("east")
    EAST,
    @SerializedName("south")
    SOUTH,
    @SerializedName("west")
    WEST,
    @SerializedName("up")
    UP,
    @SerializedName("down")
    DOWN;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
