package com.smart.spark.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 执行linux命令 帮助类
 */
public class LinuxExecUtils {

    public static void main(String[] args) {
        runtimeExec(args[0]);
    }

    public static boolean runtimeExec(String cmd) {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            if (proc.waitFor() != 0) {
                System.err.println("执行\"" + cmd + "\"时返回值=" + proc.exitValue());
                return false;
            } else {
                System.out.println("执行\"" + cmd + "\"结束");
                System.out.println("    ");
                System.out.println("    ");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static class StreamGobbler extends Thread {
        InputStream is;
        String type;

        StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null)
                    System.out.println(type + " > " + line);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
