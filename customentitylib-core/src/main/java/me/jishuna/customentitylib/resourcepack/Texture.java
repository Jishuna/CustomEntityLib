package me.jishuna.customentitylib.resourcepack;

import java.util.Arrays;

public class Texture {
    private final String id;
    private final String name;
    private final byte[] data;

    public Texture(String id, String name, byte[] data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "Texture [id=" + this.id + ", name=" + this.name + ", data=" + Arrays.toString(this.data) + "]";
    }

}
