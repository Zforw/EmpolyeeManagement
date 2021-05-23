package pers.zforw.empmgr.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
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
    protected final static String filePath = "/Users/zforw/IdeaProjects/Basic/src";

    public static void main(String[] args) throws IOException {
        root = hr.openFile(filePath + "/data.txt");
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


        /* 为输入框关联Enter键切换到密码框 */
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
        TabFolder folder = new TabFolder(mainShell, SWT.TOP);
        folder.setLayout(new FillLayout());
        folder.setBounds(0, 0, 600, 400);

        TabItem tabEdit = new TabItem(folder, SWT.NONE);
        tabEdit.setText("查找编辑");
        TabItem tabAdd = new TabItem(folder, SWT.NONE);
        tabAdd.setText("添加");
        TabItem tabCount = new TabItem(folder, SWT.NONE);
        tabCount.setText("统计");
        Group group1 = new Group(folder, SWT.NONE);
        Group group2 = new Group(folder, SWT.NONE);
        Group group3 = new Group(folder, SWT.NONE);
        Label countLabel = new Label(group3, SWT.NONE);
        countLabel.setBounds(200, 100, 200, 80);
        countLabel.setText(HR.getNum());

        Button findButton = new Button(group1, SWT.NONE);
        Button exitButton = new Button(group1, SWT.NONE);
        Button deleteButton = new Button(group1, SWT.NONE);
        Button findAllButton = new Button(group1, SWT.NONE);

        findButton.setText("查找");
        deleteButton.setText("删除");
        exitButton.setText("保存并退出");
        findAllButton.setText("查找全部");
        exitButton.setBounds(120, 280, 100, 25);
        findButton.setBounds(40, 235, 70, 25);
        deleteButton.setBounds(40, 280, 70, 25);
        findAllButton.setBounds(120, 235, 90, 25);

        final Text nameText = new Text(group1, SWT.NONE);
        final Text idNumber = new Text(group1, SWT.NONE);



        /* 只能输入整数 */
        idNumber.addVerifyListener(e -> e.doit = "0123456789".contains(e.text));
        nameText.setBounds(90, 100, 110, 20);
        idNumber.setBounds(90, 160, 110, 20);

        Label nameLabel = new Label(group1, SWT.NONE);
        nameLabel.setText("姓名：");
        nameLabel.setBounds(35, 100, 40,20);

        Label idLabel = new Label(group1, SWT.NONE);
        idLabel.setText("工号：");
        idLabel.setBounds(35, 160, 40,20);
        idLabel.pack();

        Table table = new Table(group1, SWT.BORDER);
        table.setBounds(230, 80, 300, 250);
        TableColumn tc1 = new TableColumn(table, SWT.CENTER);
        TableColumn tc2 = new TableColumn(table, SWT.CENTER);
        TableColumn tc3 = new TableColumn(table, SWT.CENTER);
        TableColumn tc4 = new TableColumn(table, SWT.CENTER);
        TableColumn tc5 = new TableColumn(table, SWT.CENTER);
        TableColumn tc6 = new TableColumn(table, SWT.CENTER);

        tc1.setText("姓名");
        tc2.setText("性别");
        tc3.setText("工号");
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

        findButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                String name = nameText.getText();
                String id = idNumber.getText();
                ArrayList<Integer> findList = new ArrayList<>();
                if(name.length() != 0 && id.length() != 0) {
                    findList = hr.find(name, Integer.parseInt(id));
                } else if (id.length() != 0){
                    findList.add(hr.find(Integer.parseInt(id)));
                } else {
                    findList = hr.find(name);
                }
                if (findList == null || findList.get(0) == -1) {
                    MessageBox msg = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.YES );
                    msg.setMessage("查无此人！\n" + ((name.length() == 0)?(" "):("姓名:" + name)) +
                            ((id.length() == 0)?(" "):(" 工号:" + id)));
                    msg.open();
                } else {
                    table.removeAll();
                    for(Integer i : findList) {
                        Empolyee empolyee = hr.get(i);
                        TableItem item = new TableItem(table, SWT.NONE);
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
                TableItem t = table.getItem(table.getSelectionIndex());
                msg.setMessage("是否要删除 " + tableItem[0].getText());
                int rc = msg.open();
                if (rc == SWT.YES) {
                    SysLog.log("delete " + t.getText() + " " + t.getText(2) + ".");
                    hr.delete(hr.find(Integer.parseInt(t.getText(2))));
                    table.remove(table.getSelectionIndex());
                }
            }
        });
        exitButton.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent e){
                display.close();
            }
        });

        Button addButton = new Button(group2, SWT.NONE);
        addButton.setBounds(80, 250, 70, 20);
        addButton.setText("添加");
        Label nLabel = new Label(group2, SWT.NONE);
        nLabel.setText("姓名:");
        nLabel.setBounds(20, 20, 90, 20);
        Text name = new Text(group2, SWT.NONE);
        name.setBounds(120, 20, 90, 20);
        Label gLabel = new Label(group2, SWT.NONE);
        gLabel.setText("性别:");
        gLabel.setBounds(20, 50, 90, 20);
        Combo gender = new Combo(group2, SWT.DROP_DOWN | SWT.READ_ONLY);
        gender.setItems("男", "女");
        gender.setBounds(116, 50, 100, 20);
        Label iLabel = new Label(group2, SWT.NONE);
        iLabel.setText("工号:");
        iLabel.setBounds(20, 80, 90, 20);
        Text id = new Text(group2, SWT.NONE);
        id.setBounds(120, 80, 90, 20);
        Label bLabel = new Label(group2, SWT.NONE);
        bLabel.setText("部门:");
        bLabel.setBounds(20, 110, 90, 20);
        Text branch = new Text(group2, SWT.READ_ONLY);
        branch.setBounds(120, 110, 90, 20);
        Label rLabel = new Label(group2, SWT.NONE);
        rLabel.setText("级别:");
        rLabel.setBounds(20, 140, 90, 20);
        Combo rank = new Combo(group2, SWT.NONE);
        rank.setBounds(117, 140, 100, 30);
        Label sLabel = new Label(group2, SWT.NONE);
        sLabel.setText("工资:");
        sLabel.setBounds(20, 170, 90, 20);
        Text salary = new Text(group2, SWT.NONE);
        salary.setBounds(120, 170, 90, 20);
        Label pLabel = new Label(group2, SWT.NONE);
        pLabel.setText("密码:");
        pLabel.setBounds(20, 200, 90, 20);
        final Text[] password = {new Text(group2, SWT.PASSWORD)};
        password[0].setBounds(120, 200, 90, 20);
        Button visualPass = new Button(group2, SWT.CHECK);
        visualPass.setText("可见");
        visualPass.setBounds(220, 200, 50, 20);
        final boolean[] visualFlag = {false};

        visualPass.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String pwd = password[0].getText();
                //password[0].dispose();
                if (!visualFlag[0]) {
                    /*
                    password[0] = new Text(group2, SWT.NONE);
                    password[0].setBounds(120, 200, 90, 20);
                    password[0].setText(pwd);
                    visualFlag[0] = true;

                     */
                    password[0].setEchoChar(' ');
                } else {
                    password[0] = new Text(group2, SWT.PASSWORD);
                    password[0].setBounds(120, 200, 90, 20);
                    password[0].setText(pwd);
                    visualFlag[0] = false;
                }
            }
        });
        Combo combo = new Combo(group2, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setItems("经理", "销售经理", "销售人员", "技术人员");
        combo.setBounds(116, 230, 100, 30);
        combo.select(0);

        combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (combo.getSelectionIndex() == 0) {
                    branch.setText("管理");
                    rank.setText("经理");
                } else if (combo.getSelectionIndex() == 1) {
                    branch.setText("销售");
                    rank.setText("经理");
                } else if (combo.getSelectionIndex() == 2) {
                    branch.setText("销售");
                    rank.setText("职员");
                } else {
                    branch.setText("开发");
                    combo.setItems("P4", "P5", "P6", "P7");
                }
            }
        });

        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                MessageBox m = new MessageBox(mainShell, SWT.OK);
                m.setText(password[0].getText());
                hr.add(name + " " + gender.getItem(gender.getSelectionIndex()) + " " + id + " " + branch + " " +
                        rank + " " + salary + " " + password[0]);
            }
        });

        tabAdd.addListener(0, event -> {

        });
        tabEdit.setControl(group1);
        tabAdd.setControl(group2);
        tabCount.setControl(group3);
    }
}
