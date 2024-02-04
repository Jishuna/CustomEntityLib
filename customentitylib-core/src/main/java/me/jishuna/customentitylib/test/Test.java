package me.jishuna.customentitylib.test;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        File file = new File("otter.bbmodel");
        System.out.println(file.getAbsolutePath());
        BBModelParser parser = BBModelParser.fromFile(file);
        parser.parse();
    }

}
