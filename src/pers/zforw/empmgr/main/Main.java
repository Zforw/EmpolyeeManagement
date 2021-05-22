package pers.zforw.empmgr.main;

import pers.zforw.empmgr.empolyee.HR;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {
    static private HR hr;
    static private String[] root;
    public static Log log = LogFactory.getLog(Main.class);
    public static void main(String[] args) throws IOException {
        openFile();
        hr.find("尹胜");
        System.out.println(hr.find(33));
        hr.delete(hr.find(33));
        System.out.println(hr.find(33));
        saveFile(hr.save(), 1024);
        /*
        String test="sdibt";
        log.info("this is info:"+test);
        log.error("this is error:"+test);
        log.debug("this is debug:"+test);
         */
    }

    static void openFile() throws IOException {
        int position = 0;

        try {
            //打开待读取的文件
            BufferedReader br = new BufferedReader(new FileReader("/Users/zforw/IdeaProjects/Basic/src/data.txt"));
            String[] bufString = new String[1024];
            String line;
            while ((line = br.readLine()) != null) {
                if(position == 0) {
                    root = line.split(" ");
                    System.out.printf("account:%s, password:%s\n", root[0], root[1]);
                }
                bufString[position] = line;
                position++;
            }
            br.close();//关闭文件
            System.out.println("open successfully!");
            /*
            for (int i = 1; i < position; i++) {
                System.out.println(bufString[i]);
            }
            */
            hr = new HR(bufString, position - 1);
        }
        catch(IOException e){
            System.out.println("Not Found!!!");
        }
    }

    static void saveFile(String[] buf, int cnt) {
        try {
            OutputStream os = new FileOutputStream("/Users/zforw/IdeaProjects/Basic/src/output.txt");
            PrintWriter pw = new PrintWriter(os);
            pw.println(root[0] + " " + root[1]);
            for (int i = 0; i < cnt; i++) {
                pw.println(buf[i]);
            }
            pw.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
