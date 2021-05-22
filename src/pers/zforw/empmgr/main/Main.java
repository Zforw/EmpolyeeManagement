package pers.zforw.empmgr.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import pers.zforw.empmgr.empolyee.HR;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {
    static HR hr = new HR();
    static String[] root;
    static Display display = new Display();
    static Shell logShell = new Shell();
    static Shell mainShell = new Shell();
    public static Log log = LogFactory.getLog(Main.class);
    private final static String fileName = "/Users/zforw/IdeaProjects/Basic/src/data.txt";
    public static void main(String[] args) throws IOException {
        root = hr.openFile(fileName);
        System.out.println(hr.getNum());
        login();
        logShell.open();
        while(!logShell.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        display.dispose();
        hr.saveFile( "/Users/zforw/IdeaProjects/Basic/src/output.txt");
    }
    private static void login() {
        logShell.setBounds(570, 200, 350, 300);
        logShell.setText("登陆界面");
        mainShell.setBounds(570 - 75, 200 - 50, 500, 400);
        mainShell.setText("主界面");
        initMainShell();

        /* ------------------登录界面核心代码------------------------ */

        /* 创建触发按钮以及按钮的显示文字和大小位置 */
        Button okButton = new Button(logShell,SWT.NONE);
        Button exitButton = new Button(logShell, SWT.NONE);

        okButton.setText("确定");
        exitButton.setText("退出");
        exitButton.setBounds(180, 235, 70, 25);
        okButton.setBounds(80, 235, 70, 25);

        final Text nameText = new Text(logShell,SWT.NONE);
        final Text passNumber = new Text(logShell,SWT.PASSWORD);
        nameText.setFocus();

        nameText.setBounds(120, 100, 110, 20);
        passNumber.setBounds(120, 160, 110, 20);

        Label nameLabel = new Label(logShell,SWT.NONE);
        nameLabel.setText("用户名：");
        nameLabel.setBounds(65, 100, 40,20);

        Label passLabel = new Label(logShell,SWT.NONE);
        passLabel.setText("密   码：");
        passLabel.setBounds(65, 165, 40, 20);


        /* 为输入框关联Enter键实现输入密码 */
        nameText.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.keyCode == 13) {
                    passNumber.setFocus();
                }
            }
        });
        /* 为密码框关联Enter键实现登录 */
        passNumber.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.keyCode == 13) {
                    onLogin(nameText.getText(), passNumber.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.keyCode == 13) {
                    onLogin(nameText.getText(), passNumber.getText());
                }
            }
        });
        /* 为确定按钮设定监听 */
        okButton.addSelectionListener(new SelectionAdapter(){
                                          public void widgetSelected(SelectionEvent e) {
                                              onLogin(nameText.getText(), passNumber.getText());
                                          }
                                      }

        );
        /* 为退出按钮设定监听 */
        exitButton.addSelectionListener(new SelectionAdapter(){

                                            public void widgetSelected(SelectionEvent e){
                                                display.close();
                                            }
                                        }
        );

    }
    /**
     * @description: 登录代码，判断账号密码是否正确
     * @param: [name, pass]
     * @return: void
     */
    static void onLogin(String name, String pass) {
        /* 判断账号密码是否正确 */
        if(root[0].equals(name) && root[1].equals(pass)){
            //MessageDialog.openWarning(shell, "恭喜", "欢迎"+nameText.getText()+"登录");
            MessageBox msg = new MessageBox(logShell, SWT.ICON_INFORMATION | SWT.YES );

            msg.setMessage("欢迎" + name + "登录");
            int rc = msg.open();

            SysLog.writeFile(name + " logged in.");

            mainShell.open();
            logShell.dispose();
            while(!mainShell.isDisposed())
                if(!display.readAndDispatch())
                    display.sleep();
            display.dispose();

        } else{
            //MessageDialog.openError(shell, "错误","请输入正确的用户名或密码！");
            MessageBox msg = new MessageBox(logShell, SWT.ICON_WARNING | SWT.YES );

            msg.setMessage("请输入正确的用户名或密码！" + name + pass);
            int rc = msg.open();
        }


    }

    private static void initMainShell() {
        Button findButton = new Button(mainShell,SWT.NONE);
        Button exitButton = new Button(mainShell, SWT.NONE);

        findButton.setText("查找");
        exitButton.setText("保存并退出");
        exitButton.setBounds(180, 235, 70, 25);
        findButton.setBounds(80, 235, 70, 25);

        final Text nameText = new Text(mainShell,SWT.NONE);
        final Text passNumber = new Text(mainShell,SWT.PASSWORD);
        nameText.setBounds(120, 100, 110, 20);
        passNumber.setBounds(120, 160, 110, 20);

        Label nameLabel = new Label(mainShell,SWT.NONE);
        nameLabel.setText("姓名：");
        nameLabel.setBounds(65, 100, 40,20);



        exitButton.addSelectionListener(new SelectionAdapter(){
                                            public void widgetSelected(SelectionEvent e){
                                                hr.saveFile( "/Users/zforw/IdeaProjects/Basic/src/output.txt");
                                                display.close();
                                            }
                                        }
        );
    }

}
