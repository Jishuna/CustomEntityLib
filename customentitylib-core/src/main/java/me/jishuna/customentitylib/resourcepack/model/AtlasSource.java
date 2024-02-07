package me.jishuna.customentitylib.resourcepack.model;

import me.jishuna.customentitylib.Utils;

public class AtlasSource {
    public String type;
    public String source;
    public String prefix;

    public AtlasSource() {
        this(null, null, null);
    }

    public AtlasSource(String type, String source, String prefix) {
        this.type = Utils.getOr(type, () -> "");
        this.source = Utils.getOr(source, () -> "");
        this.prefix = Utils.getOr(prefix, () -> "");
    }

}
