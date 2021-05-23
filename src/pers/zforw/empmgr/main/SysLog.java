package pers.zforw.empmgr.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/22 3:53 下午
 * @project: Basic
 * @description:
 */
public class SysLog {
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

        // 在文件夹目录下新建文件
        File file = new File("/Users/zforw/IdeaProjects/Basic/src/log.txt");

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        String str = null;
        try {
            if (!file.exists()) {
                boolean hasFile = file.createNewFile();
                if (hasFile) {
                    str = getDate() + "log.txt not exists, create a new file.";
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
}
