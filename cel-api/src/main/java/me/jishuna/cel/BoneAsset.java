package me.jishuna.cel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.sampling.BestCandidateSampling.Cube;

public class BoneAsset {
    private final String name;
    private final Vector3f pivot;
    private final int customModelData;
    private final Vector3f offset;
    private final List<Cube> cubes;
    private final boolean small;
    private final Map<String, BoneAsset> children;

    public BoneAsset(String name, Vector3f pivot, int customModelData, Vector3f offset, List<Cube> cubes, boolean small, Map<String, BoneAsset> children) {
        this.name = name;
        this.pivot = pivot;
        this.customModelData = customModelData;
        this.offset = offset;
        this.cubes = cubes;
        this.small = small;
        this.children = children;
    }

    public String name() {
        return name;
    }

    public Vector3f pivot() {
        return pivot;
    }

    public int customModelData() {
        return customModelData;
    }

    public Vector3f offset() {
        return offset;
    }

    public List<Cube> cubes() {
        return cubes;
    }

    public boolean small() {
        return small;
    }

    public Collection<BoneAsset> children() {
        return children.values();
    }

}
