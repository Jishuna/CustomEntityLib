package me.jishuna.customentitylib.model;

import com.google.gson.JsonObject;
import java.util.Map;
import org.joml.Vector3f;

public class Cube {
    private final Vector3f from;
    private final Vector3f to;
    private final CubeRotation rotation;
    private final Map<Face, CubeFace> faces;

    public Cube(Vector3f from, Vector3f to, CubeRotation rotation, Map<Face, CubeFace> faces) {
        this.from = from;
        this.to = to;
        this.rotation = rotation;
        this.faces = faces;
    }

    public Vector3f getFrom() {
        return new Vector3f(this.from);
    }

    public Vector3f getTo() {
        return new Vector3f(this.to);
    }

    public CubeRotation getRotation() {
        return this.rotation;
    }

    public Map<Face, CubeFace> getFaces() {
        return this.faces;
    }

    public JsonObject asJsonObject() {
        JsonObject root = new JsonObject();
        root.add("from", BBModelParser.GSON.toJsonTree(new float[] { this.from.x, this.from.y, this.from.z }));
        root.add("to", BBModelParser.GSON.toJsonTree(new float[] { this.to.x, this.to.y, this.to.z }));

        root.add("rotation", this.rotation.asJsonObject());

        JsonObject faceObject = new JsonObject();
        this.faces.forEach((face, cubeFace) -> faceObject.add(face.toString().toLowerCase(), cubeFace.asJsonObject()));
        root.add("faces", faceObject);

        return root;
    }

    @Override
    public String toString() {
        return "Cube [from=" + this.from + ", to=" + this.to + ", rotation=" + this.rotation + ", faces=" + this.faces + "]";
    }
}
