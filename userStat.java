package user.stat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.server.UID;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            String s;
            String[] StrArray;
            int UserCount = 0;
            int UIDCount = 0;
            int ShellCount = 0;
            int EmptyComment = 0;
            while ((s = br.readLine()) != null) {
                StrArray = s.split(":");
                if (StrArray.length == 7) {
                    UserCount++;
                    if (StrArray[2].equals("0")) {
                        UIDCount++;
                    }
                    if (StrArray[6].equals("/sbin/nologin")) {
                        ShellCount++;
                    }
                    if (StrArray[4].equals("")){
                        EmptyComment ++;
                    }
                }
            }
            System.out.println("Users: " + UserCount);
            System.out.println("Users with UID 0: " + UIDCount);
            System.out.println("Users with /sbin/nologin shell: " + ShellCount);
            System.out.println("Users with empty comment field: " + EmptyComment);
        } catch (Exception e){
            System.out.println("Error");
        }

    }
}