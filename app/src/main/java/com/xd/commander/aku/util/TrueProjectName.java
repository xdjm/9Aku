package com.xd.commander.aku.util;

public class TrueProjectName {
    public static String trueProject(String projectName){
        return projectName.replace(" ","-");
    }
}
