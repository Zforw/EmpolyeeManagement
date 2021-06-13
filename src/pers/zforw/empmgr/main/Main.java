package pers.zforw.empmgr.main;

import pers.zforw.empmgr.employee.HR;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/27 7:07 下午
 * @project: EmpMgr
 * @description: 公司员工管理系统
 * java -XstartOnFirstThread -jar xxx.jar
 */
public class Main {
    public static HR hr = new HR();
    protected static boolean isLogin = false;
    protected static Display display = new Display();
    protected static Shell logShell = new Shell(display);
    protected static Shell mainShell = new Shell(display);
    protected static String filePath = "/Users/zforw/IdeaProjects/Basic/src/";
    protected static String OS;

    public static void main(String[] args) throws IOException {
        System.out.println(Func.levenshtein("hamchenoonan", "hamchenin"));

        OS = System.getProperty("os.name").toLowerCase();
        if(OS.indexOf("windows") > 0) {
            filePath = "C:\\";
        }

        /*
        File directory = new File("");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        }catch(Exception e){}
         */

        try {
            hr.loadFile(filePath + "data.txt");
        } catch (IOException e) {
            MessageBox msg = new MessageBox(logShell, SWT.ICON_WARNING | SWT.YES);
            msg.setMessage(e.getMessage());
            msg.open();
            Func.log(e.getMessage());
            return;
        }
        Func.log("load file data.txt");


        LoginShell.login();
        logShell.open();

        while(!mainShell.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        if (!display.isDisposed()) {
            display.dispose();
        }
        if (isLogin) Func.log(HR.name + " logged out");
        try {
            hr.saveFile(filePath + "data.txt");
        } catch (IOException | SQLException | ClassNotFoundException e) {
            Func.log(e.getMessage());
            return;
        }
        Func.log("store file data.txt");
    }
}
