package cz.cuni.mff.java.homework.jfind;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class JFind {
    static String[] arguments;
    static boolean match;
    static String regular_expression;
    static int size = -2;
    static int bytes = -2;
    static int file_size;

    public static void wrong_parameter(){
        System.out.println("ERROR");
    }

    public static void find(String[] args) {
        if (args.length < 1) {
            wrong_parameter();
            return;
        }

        arguments = args;
        boolean success = search_arguments();
        if (!success) {
            return;
        }

        recursive_filesearch(new File(arguments[0]).toPath());
    }

    private static void recursive_filesearch(Path SPath) {
        if (!SPath.toFile().isDirectory()){
            return;
        }
        try(DirectoryStream<Path>Stream= Files.newDirectoryStream(SPath)) {
            for (Path p: Stream){
                if (p.toFile().isDirectory()) {
                    recursive_filesearch(p);
                }
                else {
                    long fsize = Files.size(p);
                    if (bytes == 0) {
                        fsize /= 1024;
                    } else if (bytes == 1){
                        fsize /= 1024;
                        fsize /= 1024;
                    }

                    if (size == 0 && fsize != file_size) {
                        continue;
                    }
                    if (size == -1 && fsize > file_size) {
                        continue;
                    }
                    if (size == 1 && fsize < file_size) {
                        continue;
                    }

                    if(regular_expression != null) {
                        Pattern pattern = Pattern.compile(regular_expression);
                        if(p.toFile().getName().matches(pattern.pattern())) {
                            String fileAbs = p.toFile().getAbsolutePath();
                            System.out.println(Paths.get(arguments[0]).toAbsolutePath().relativize(Paths.get(fileAbs).toAbsolutePath()));
                        }
                    }
                    else {
                        String fileAbs = p.toFile().getAbsolutePath();
                        System.out.println(Paths.get(arguments[0]).relativize(Paths.get(fileAbs)));
                    }
                }
            }
        } catch (Exception e){
            wrong_parameter();
        }
    }


    private static boolean search_arguments() {
        for (int i=1; i < arguments.length; i=i+2){
            switch(arguments[i]){
                case "-name" -> {
                    match = true;
                    boolean success = parse_name(i);
                    if (!success){
                        wrong_parameter();
                        return false;
                    }
                }
                case "-iname" -> {
                    match = false;
                    boolean success = parse_name(i);
                    if (!success){
                        wrong_parameter();
                        return false;
                    }
                }
                case "-regex" -> {
                    boolean success = parse_regex(i);
                    if (!success){
                        wrong_parameter();
                        return false;
                    }
                }
                case "-size" -> {
                    size = 0;
                    boolean success = parse_size(i);
                    if (!success){
                        wrong_parameter();
                        return false;
                    }
                }
                case "-ssize" -> {
                    size = -1;
                    boolean success = parse_size(i);
                    if (!success){
                        wrong_parameter();
                        return false;
                    }
                }
                case "-bsize" -> {
                    size = 1;
                    boolean success = parse_size(i);
                    if (!success){
                        wrong_parameter();
                        return false;
                    }
                }
                default -> {
                    wrong_parameter();
                    return false;
                }
            }
        } return true;
    }

    private static boolean parse_size(int i) {
        i++;
        if (i >= arguments.length){
            return false;
        }
        String value;
        if (arguments[i].endsWith("k")){
            bytes = 0;
            value = arguments[i].substring(0,arguments[i].length()-1);
        } else if (arguments[i].endsWith("M")){
            bytes = 1;
            value = arguments[i].substring(0,arguments[i].length()-1);
        } else {
            bytes = -1;
            value = arguments[i];
        }

        try {
            file_size = Integer.parseInt(value);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    private static boolean parse_regex(int i) {
        i++;
        if (i >= arguments.length){
            return false;
        }
        regular_expression = arguments[i];
        return true;
    }

    private static boolean parse_name(int i) {
        i++;
        if (i >= arguments.length){
            return false;
        }
        String temp = arguments[i];
        temp = temp.replaceAll("\\?", ".");
        temp = temp.replaceAll("\\*", ".*");

        if (match) {
            regular_expression = temp;
            return true;
        }
        regular_expression = "(?i)" + temp;
        return true;
    }
}