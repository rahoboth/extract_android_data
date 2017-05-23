package com.data.commands;

import com.sun.javafx.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammed.rampurawala on 5/23/2017.
 */
public abstract class BaseCommands {
    protected ArrayList<String> macCommands = new ArrayList<>();
    protected ArrayList<String> adbShell = new ArrayList<>();

    protected String packageName;


    private String startAdbServer = "adb start-server";

    protected String basePath = "/sdcard/";


    {
        //Add Mac NonRootCommands
        macCommands.add("/bin/bash");
        macCommands.add("-l");
        macCommands.add("-c");

        //Add adb shell
        adbShell.add("adb");
        adbShell.add("shell");
    }

    public void setPackageName(String packageName) {
        basePath += packageName;
        this.packageName = packageName;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getStartAdbServerCommand() {
        return startAdbServer;
    }

    public ArrayList<String> getPullFileCommand(String fileMapKey, String fileName, String outputPath) {
        boolean isMac = Utils.isMac();
        ArrayList<String> listOfCommands = new ArrayList<>();
        if (isMac) listOfCommands.addAll(macCommands);
        listOfCommands.add("adb");
        listOfCommands.add("pull");
        String inputFile = getBasePath() + "/" + fileMapKey + fileName;
        //Input file name path
        listOfCommands.add(inputFile);
        String outputFile = "\"" + outputPath + File.separator + fileMapKey + "\"";
        //Output file name path
        listOfCommands.add(outputFile);
        System.out.println("Input:->" + inputFile);
        System.out.println("Output:->" + outputFile);
        System.out
                .println("-----------------------------------------------------");
        return listOfCommands;
    }

    public ArrayList<String> getDeleteDirectoryCommand() {
        ArrayList<String> listOfCommands = new ArrayList<>(adbShell);
        listOfCommands.add("rm -r " + basePath);
        return listOfCommands;
    }

    public ArrayList<String> createDirInSdcard(String folderName) {
        System.out.println(getBasePath() + "/" + folderName);
        ArrayList<String> listOfCommands = new ArrayList<String>(adbShell);
        listOfCommands.add("mkdir " + getBasePath() + "/" + folderName);
        return listOfCommands;
    }


    public List<String> getCreatePackDirInSdcard() {
        ArrayList<String> listOfCommands = new ArrayList<String>(adbShell);
        listOfCommands.add("mkdir " + getBasePath());
        return listOfCommands;
    }

    public abstract String[][] getCommandToFetchDataToSdCard(String packageName, String fileMapKey, String fileName);

    public abstract List<String> getListOfFilesForPackage(String packageName, String key, boolean isRooted);

    public abstract List<String> getMainDirPackage(String packageName, boolean isRooted);
}
