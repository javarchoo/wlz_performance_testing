package com.example.jjwlzperformancetesting;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

public class ShellExecutor {
    public ShellExecutor() {
        System.out.println("@@생성");
    }

    public String execute(String command) {
        return execute(command, false)[1];
    }

    public String[] execute(String command, boolean lastLineFlag) {

        String[] re = {"", ""};
        StringBuffer output = new StringBuffer();
        String lastLine = "";

        java.lang.Process proc;
        try {
            proc = Runtime.getRuntime().exec(command);
            System.out.println("@@실행command: " + command);
            proc.waitFor(); //프로세스의 명령이 끝날때까지 대기한다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
                lastLine = line;
            }

            System.out.println(output.toString());
            reader.close();
            reader = null;
            proc.destroy();
            proc = null;
        } catch (Exception e) {
            System.out.println("@@에러발생");
            e.printStackTrace();
        }
        System.out.println("@@실행완료");

        re[0] = lastLine;
        re[1] = output.toString();

        return re;
/*
        if(lastLineFlag) {
            return lastLine;
        } else {
            return output.toString();
        }
*/

/*
        String[] commands = {"/data/user/0/com.example.jjwlzperformancetesting/files/iperf3", "-c", "223.62.93.226"};

        StringBuilder cmdReturn = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            int c;
            while ((c = inputStream.read()) != -1) {
                cmdReturn.append((char) c);
            }

//            System.out.println(cmdReturn.toString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cmdReturn.toString();
 */
    }


    public String execute2(String command) {

        StringBuffer output = new StringBuffer();

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = rt.exec(command);
            proc.waitFor(); //프로세스의 명령이 끝날때까지 대기한다.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

// Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while (true) {
            try {
                if (!((s = stdInput.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(s);
            output.append(s);
        }

// Read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while (true) {
            try {
                if (!((s = stdError.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(s);
            output.append(s);
        }

        try {
            stdInput.close();
            stdInput = null;
            stdError.close();
            stdError = null;
            proc.destroy();
            proc = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
