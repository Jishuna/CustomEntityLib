package me.jishuna.customentitylib.test;

import java.io.File;
import me.jishuna.customentitylib.model.BBModelParser;
import me.jishuna.customentitylib.model.EntityModel;
import me.jishuna.customentitylib.resourcepack.ResourcePackBuilder;

public class Test {

    public static void main(String[] args) {
        File file = new File("otter.bbmodel");
        System.out.println(file.getAbsolutePath());
        BBModelParser parser = BBModelParser.fromFile(file);
        EntityModel model = parser.parse();

        File rpFile = new File("resource-pack");
        ResourcePackBuilder builder = new ResourcePackBuilder(rpFile, "test");
        builder.writeModel(model);
    }
}
