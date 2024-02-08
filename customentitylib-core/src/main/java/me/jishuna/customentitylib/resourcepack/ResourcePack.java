package me.jishuna.customentitylib.resourcepack;

import com.google.common.base.Supplier;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import me.jishuna.customentitylib.GsonUtil;
import me.jishuna.customentitylib.resourcepack.model.Atlas;
import me.jishuna.customentitylib.resourcepack.model.ItemModel;

public class ResourcePack {
    private static final String ASSETS_PATH = "/assets";
    private static final String MODELS_PATH = "/models";
    private static final String TEXTURES_PATH = "/textures";

    private final Map<Path, Object> modified = new HashMap<>();

    private final FileSystem source;

    private ResourcePack(FileSystem source) {
        this.source = source;
    }

    public static ResourcePack fromFile(File file, boolean delete) {
        Path path = file.toPath();

        try {
            if (delete) {
                Files.deleteIfExists(path);
            }

            URI uri = path.toUri();
            URI zipUri = new URI("jar:" + uri.getScheme(), uri.getPath(), null);
            FileSystem system = FileSystems.newFileSystem(zipUri, Map.of("create", "true"));
            return new ResourcePack(system);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        this.modified.forEach((path, entry) -> {
            try (Writer writer = Files.newBufferedWriter(path)) {
                GsonUtil.GSON.toJson(entry, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            this.source.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PackMeta getPackMeta() {
        Path path = this.source.getPath("/pack.mcmeta");

        Object existing = this.modified.computeIfAbsent(path, k -> getAs(path, PackMeta.class, PackMeta::new));
        if (existing instanceof PackMeta meta) {
            return meta;
        }

        return null;
    }

    public Atlas getAtlas(String name) {
        Path path = this.source.getPath(ASSETS_PATH, "minecraft", "atlases", name);

        Object existing = this.modified.computeIfAbsent(path, k -> getAs(path, Atlas.class, Atlas::new));
        if (existing instanceof Atlas atlas) {
            return atlas;
        }

        return null;
    }

    public ItemModel getItemModel(String namespace, String name) {
        Path path = this.source.getPath(ASSETS_PATH, namespace, MODELS_PATH, name);

        Object existing = this.modified.computeIfAbsent(path, k -> getAs(path, ItemModel.class, ItemModel::new));
        if (existing instanceof ItemModel model) {
            return model;
        }

        return null;
    }

    public void writeTexture(String namespace, String name, Texture texture) {
        Path path = this.source.getPath(ASSETS_PATH, namespace, TEXTURES_PATH, name);
        createIfMissing(path);

        try {
            Files.write(path, texture.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> T getAs(Path path, Class<T> type, Supplier<T> supplier) {
        createIfMissing(path);

        T value = null;
        try (Reader reader = Files.newBufferedReader(path)) {
            value = GsonUtil.GSON.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value != null ? value : supplier.get();
    }

    private static void createIfMissing(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
