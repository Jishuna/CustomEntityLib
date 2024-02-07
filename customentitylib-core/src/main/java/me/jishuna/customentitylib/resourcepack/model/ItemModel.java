package me.jishuna.customentitylib.resourcepack.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ItemModel {
    public final Map<String, String> textures;
    public final List<ModelElement> elements;
    public final Set<ModelOverride> overrides;
    public String parent;

    public ItemModel() {
        this.textures = new LinkedHashMap<>();
        this.elements = new ArrayList<>();
        this.overrides = new TreeSet<>();
        this.parent = null;
    }

}
