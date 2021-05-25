package pers.zforw.empmgr.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.*;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/22 3:53 下午
 * @project: Basic
 * @description:
 */
public class Func {
    /***
     * @description: 定义时间格式
     * @param: []
     * @return: [yyyy-MM-dd HH:mm:ss]
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
        Date date = new Date();
        return df.format(date);
    }
    /**
     * @description: 在日志中追加信息
     * @param: [content]
     * @return: void
     */
    public static void log(String content) {
        File file = new File(Main.filePath + "log.txt");

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        String str = null;
        try {
            if (!file.exists()) {
                boolean hasFile = file.createNewFile();
                if (hasFile) {
                    str = getDate() + "log.txt not exists, create a new file";
                }
                fos = new FileOutputStream(file);
            } else {
                fos = new FileOutputStream(file, true);
            }

            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            if(str != null) {
                osw.write(str);
                osw.write("\r\n");
            }
            osw.write(getDate() + content);
            osw.write("\r\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 关闭流 */
            try {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 用空格将字符串分割
     *      用于处理当两个子字符串之间多于一个空格的特殊情况
     * @param: [str]
     * @return:
     */
    public static String[] Split(String str) {
        String[] result = str.split(" ");
        for (int i = 0; i < result.length; i++) {
            /*  */
            if (result[i].length() == 0 && i < result.length - 1) {
                String t = result[i];
                result[i] = result[i + 1];
                result[i + 1] = t;
            }
        }
        return result;
    }


    /*
     * @description: 目录不存在则创建目录
     * @param: [fileDir]
     * @return:
     */
    public static void createDir(String fileDir) {
        File file = new File(fileDir);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            file.mkdirs();
        }
    }
}
