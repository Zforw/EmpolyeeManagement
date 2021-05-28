package pers.zforw.empmgr.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pers.zforw.empmgr.employee.HR;

import java.io.IOException;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/27 7:07 下午
 * @project: EmpMgr
 * @description: 公司员工管理系统
 */
public class Main {
    static HR hr = new HR();
    protected static boolean isLogin = false;
    static String[] root;
    protected static Display display = new Display();
    protected static Shell logShell = new Shell(display);
    protected static Shell mainShell = new Shell(display);
    protected static String filePath = "/Users/zforw/IdeaProjects/Basic/src/";
    protected static String OS;

    public static void main(String[] args) throws IOException {


        /*
        OS = System.getProperty("os.name").toLowerCase();
        if(OS.indexOf("windows") > 0) {

        } else {

        }
         */


        try {
            root = hr.loadFile(filePath + "output.txt");
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
        if (isLogin) Func.log(root[0] + " logged out");
        try {
            hr.saveFile(filePath + "output.txt");
        } catch (IOException e) {
            Func.log(e.getMessage());
            return;
        }
        Func.log("store file output.txt");

    }
}
