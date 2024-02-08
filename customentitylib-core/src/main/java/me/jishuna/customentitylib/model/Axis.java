package me.jishuna.customentitylib.model;

import com.google.gson.annotations.SerializedName;

public enum Axis {
    @SerializedName("x")
    X,
    @SerializedName("y")
    Y,
    @SerializedName("z")
    Z;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
