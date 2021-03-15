package com.example.jjwlzperformancetesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellExecutor {
    public ShellExecutor() {
        System.out.println("@@생성");
    }

    public String execute(String command) {

        StringBuffer output = new StringBuffer();

        java.lang.Process proc;
        try {
            proc = Runtime.getRuntime().exec(command);
            System.out.println("@@실행command: " + command);
            proc.waitFor(); //프로세스의 명령이 끝날때까지 대기한다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            reader.close();
            reader = null;
            proc.destroy();
            proc = null;
        } catch (Exception e) {
            System.out.println("@@에러발생");
            e.printStackTrace();
        }
        System.out.println("@@실행완료/ 결과: ");
        System.out.println(output.toString());
        return output.toString();
    }
}
