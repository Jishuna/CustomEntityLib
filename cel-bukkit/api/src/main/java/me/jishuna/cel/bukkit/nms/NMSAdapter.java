package me.jishuna.cel.bukkit.nms;

import org.bukkit.Bukkit;

import me.jishuna.cel.bukkit.entity.CustomEntityType;

public abstract class NMSAdapter {
    private static final String CLASS_NAME = "me.jishuna.cel.bukkit.nms.%version%.NMSAdapterImpl";
    private static NMSAdapter instance;

    public static NMSAdapter getInstance() {
        if (instance == null) {
            try {
                instance = (NMSAdapter) Class.forName(CLASS_NAME.replace("%version%", getServerVersion())).getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
            }
        }
        return instance;
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toLowerCase();
    }
    
    public abstract void onLoad();
    
    public abstract void onEnable();
    
    public abstract void registerInternalEntityType(CustomEntityType type);

}
