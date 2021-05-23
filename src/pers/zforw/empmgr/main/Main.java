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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import pers.zforw.empmgr.empolyee.Empolyee;
import pers.zforw.empmgr.empolyee.HR;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static HR hr = new HR();
    static boolean isLogin = false;
    static String[] root;
    static Display display = new Display();
    static Shell logShell = new Shell(display);
    static Shell mainShell = new Shell(display);
    private final static String filePath = "/Users/zforw/IdeaProjects/Basic/src";

    public static void main(String[] args) throws IOException {
        root = hr.openFile(filePath + "/data.txt");
        System.out.println(hr.getNum());
        login();
        logShell.open();

        while(!mainShell.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        if (!display.isDisposed()) {
            display.dispose();
        }
        if (isLogin) SysLog.log(root[0] + " logged out.");
        hr.saveFile( filePath + "/output.txt");
    }
    private static void login() {
        logShell.setBounds(570, 200, 350, 300);
        logShell.setText("登陆界面");
        mainShell.setBounds(570 - 75, 200 - 50, 600, 400);
        mainShell.setText("主界面");

        /* ------------------登录界面代码------------------------ */
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
            @Override
            public void widgetSelected(SelectionEvent e) {
                onLogin(nameText.getText(), passNumber.getText());
            }
        });
        /* 为退出按钮设定监听 */
        exitButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e){
                display.close();
            }
        });

    }
    /**
     * @description: 登录代码，判断账号密码是否正确
     * @param: [name, pass]
     * @return: void
     */
    static void onLogin(String name, String pass) {
        /* 判断账号密码是否正确 */
        if(root[0].equals(name) && root[1].equals(pass)){
            MessageBox msg = new MessageBox(logShell, SWT.ICON_INFORMATION | SWT.YES );

            msg.setMessage("欢迎" + name + "登录!");
            msg.open();

            isLogin = true;
            SysLog.log(name + " logged in.");

            mainShell.open();
            initMainShell();
            logShell.dispose();

            while(!mainShell.isDisposed())
                if(!display.readAndDispatch())
                    display.sleep();
            display.dispose();
        } else{
            MessageBox msg = new MessageBox(logShell, SWT.ICON_WARNING | SWT.YES );
            msg.setMessage("用户名或密码错误！");
            msg.open();
        }
    }

    private static void initMainShell() {
        Button findButton = new Button(mainShell,SWT.NONE);
        Button exitButton = new Button(mainShell, SWT.NONE);
        Button deleteButton = new Button(mainShell, SWT.NONE);
        Button findAllButton = new Button(mainShell, SWT.NONE);

        findButton.setText("查找");
        deleteButton.setText("删除");
        exitButton.setText("保存并退出");
        findAllButton.setText("查找全部");
        exitButton.setBounds(150, 235, 100, 25);
        findButton.setBounds(50, 235, 70, 25);
        deleteButton.setBounds(150, 280, 70, 25);
        findAllButton.setBounds(50, 280, 70, 25);
        findAllButton.pack();
        deleteButton.pack();

        final Text nameText = new Text(mainShell,SWT.NONE);
        final Text idNumber = new Text(mainShell,SWT.NONE);

        idNumber.addVerifyListener(e -> { // 只能输入数值
            e.doit = "0123456789".contains(e.text);
        });
        nameText.setBounds(90, 100, 110, 20);
        idNumber.setBounds(90, 160, 110, 20);

        Label nameLabel = new Label(mainShell, SWT.NONE);
        nameLabel.setText("姓名：");
        nameLabel.setBounds(35, 100, 40,20);

        Label idLabel = new Label(mainShell, SWT.NONE);
        idLabel.setText("ID：");
        idLabel.setBounds(35, 160, 40,20);
        idLabel.pack();

        Table table = new Table(mainShell, SWT.BORDER);
        table.setBounds(230, 100, 300, 250);
        TableColumn tc1 = new TableColumn(table, SWT.CENTER);
        TableColumn tc2 = new TableColumn(table, SWT.CENTER);
        TableColumn tc3 = new TableColumn(table, SWT.CENTER);
        TableColumn tc4 = new TableColumn(table, SWT.CENTER);
        TableColumn tc5 = new TableColumn(table, SWT.CENTER);
        TableColumn tc6 = new TableColumn(table, SWT.CENTER);

        tc1.setText("姓名");
        tc2.setText("性别");
        tc3.setText("ID");
        tc4.setText("部门");
        tc5.setText("级别");
        tc6.setText("工资");
        tc1.setWidth(70);
        tc2.pack();
        tc3.setWidth(50);
        tc4.setWidth(40);
        tc5.setWidth(40);
        tc6.setWidth(50);

        table.setHeaderVisible(true);
        final ArrayList<Integer>[] findList = new ArrayList[]{null};

        findButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                String name = nameText.getText();
                String id = idNumber.getText();
                if(name.length() != 0 && id.length() != 0) {
                    findList[0] = hr.find(name, Integer.parseInt(id));
                } else if (id.length() != 0){
                    findList[0] = hr.find(Integer.parseInt(id));
                } else {
                    findList[0] = hr.find(name);
                }
                if (findList[0] == null) {
                    MessageBox msg = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.YES );
                    msg.setMessage("查无此人！\n" + ((name.length() == 0)?(" "):("姓名:" + name)) +
                            ((id.length() == 0)?(" "):(" ID:" + id)));
                    msg.open();
                } else {
                    table.removeAll();
                    for(Integer i : findList[0]) {
                        Empolyee empolyee = hr.get(i);
                        TableItem item = new TableItem(table,SWT.NONE);
                        item.setText(new String[]{empolyee.getName(), empolyee.getGender(),String.valueOf(empolyee.getId()),
                                                empolyee.getBranch(), empolyee.getRank(), String.valueOf(empolyee.getSalary())});
                    }
                }

            }
        }
        );

        findAllButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                table.removeAll();
                for (int i = 0;i < hr.getSize();i++) {
                    Empolyee empolyee = hr.get(i);
                    TableItem item = new TableItem(table,SWT.NONE);
                    item.setText(new String[]{empolyee.getName(), empolyee.getGender(),String.valueOf(empolyee.getId()),
                            empolyee.getBranch(), empolyee.getRank(), String.valueOf(empolyee.getSalary())});
                }
            }
        });

        deleteButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                MessageBox msg = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
                TableItem[] tableItem = table.getSelection();
                msg.setMessage("是否要删除 " + tableItem[0].getText());
                int rc = msg.open();
                if (rc == SWT.YES) {
                    SysLog.log("delete " + tableItem[0].getText() + ".");
                    table.remove(table.getSelectionIndex());
                }
            }
        });
        exitButton.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent e){
                display.close();
            }
        });

    }
}
