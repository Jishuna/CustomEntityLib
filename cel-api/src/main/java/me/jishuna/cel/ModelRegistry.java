package me.jishuna.cel;

import java.util.HashMap;
import java.util.Map;

public class ModelRegistry {
    private final Map<String, Model> registryMap = new HashMap<>();

    public void register(String key, Model type) {
        this.registryMap.put(key, type);
    }

    public Model get(String key) {
        return this.registryMap.get(key);
    }
}
