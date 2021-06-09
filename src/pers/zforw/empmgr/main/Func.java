package pers.zforw.empmgr.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * @description: 创建目录
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


    /*
     * @description: 将字符串转换为一个大整数
     * @param: [msg]
     * @return:
     */
    public static String encrypt(String msg) throws UnsupportedEncodingException {
        msg = java.net.URLEncoder.encode(msg,"GBK");
        byte[] text = msg.getBytes("GBK");//将字符串转换成byte类型数组，实质是各个字符的二进制形式
        for (int i = 0;i < text.length;i++) {
            text[i] += 23;//偏移
        }
        BigInteger m = new BigInteger(text);
        return m.toString();
    }

    /*
     * @description: 解密函数
     * @param: [encoded]
     * @return:
     */
    public static String decrypt(String encoded) throws UnsupportedEncodingException {
        BigInteger m = new BigInteger(encoded);//二进制串转换为一个大整数
        byte[] mt = m.toByteArray();//m为密文的BigInteger类型
        for (int i = 0; i < mt.length;i++) {
            mt[i] -= 23;
        }
        String str = new String(mt,"GBK");
        str=java.net.URLDecoder.decode(str,"GBK");
        return str;
    }

    /*
     * @description: 计算两个字符串之间的相似度
     * @param: [str1, str2]
     * @return:
     */
    public static float levenshtein(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        //建立数组
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = Math.min(Math.min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1),
                        dif[i - 1][j] + 1);
            }
        }
        //取数组右下角的值，同样不同位置代表不同字符串的比较，差异步骤：dif[len1][len2]);
        //计算相似度
        return 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
    }

}
