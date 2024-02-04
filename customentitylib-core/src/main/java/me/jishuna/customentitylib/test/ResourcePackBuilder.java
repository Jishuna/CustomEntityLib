package me.jishuna.customentitylib.test;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ResourcePackBuilder {
    private final File folder;

    public ResourcePackBuilder(File folder) {
        this.folder = folder;
        folder.mkdirs();
    }

    public void writeBone(Bone bone) {
        File file = new File(this.folder, bone.getName() + ".json");
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            BBModelParser.GSON.toJson(bone.asJsonObject(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
