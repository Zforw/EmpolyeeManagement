package pers.zforw.empmgr.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class Main {
    static HR hr = new HR();
    static boolean isLogin = false;
    static String[] root;
    static Display display = new Display();
    static Shell logShell = new Shell(display);
    static Shell mainShell = new Shell(display);
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
        if (isLogin) Func.log(root[0] + " logged out");
        try {
            hr.saveFile(filePath + "output.txt");
        } catch (IOException e) {
            return;
        }
        Func.log("store file output.txt");

    }
    private static void login() {
        logShell.setBounds(570, 200, 350, 300);
        logShell.setText("登录界面");
        mainShell.setBounds(570 - 75, 200 - 50, 600, 500);
        mainShell.setText("主界面");

        /* ---------------------登录界面代码------------------------ */
        Button okButton = new Button(logShell,SWT.NONE);
        Button exitButton = new Button(logShell, SWT.NONE);

        okButton.setText("确定");
        exitButton.setText("退出");
        exitButton.setBounds(180, 235, 70, 25);
        okButton.setBounds(80, 235, 70, 25);

        final Text nameText = new Text(logShell, SWT.NONE);
        final Text passNumber = new Text(logShell, SWT.PASSWORD);
        nameText.setFocus();

        nameText.setBounds(120, 100, 110, 20);
        passNumber.setBounds(120, 160, 110, 20);

        Label nameLabel = new Label(logShell, SWT.NONE);
        nameLabel.setText("用户名：");
        nameLabel.setBounds(65, 100, 40,20);

        Label passLabel = new Label(logShell, SWT.NONE);
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
            Func.log(name + " logged in");

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
        folder.setBounds(0, 0, 600, 500);

        TabItem tabEdit = new TabItem(folder, SWT.NONE);
        tabEdit.setText("查找编辑");
        TabItem tabAdd = new TabItem(folder, SWT.NONE);
        tabAdd.setText("添加");
        TabItem tabCount = new TabItem(folder, SWT.NONE);
        tabCount.setText("统计");
        Group group1 = new Group(folder, SWT.NONE);
        //Group group2 = new Group(folder, SWT.NONE);
        Group group3 = new Group(folder, SWT.NONE);
        Label countLabel = new Label(group3, SWT.NONE);
        countLabel.setBounds(200, 100, 200, 80);
        countLabel.setText(HR.getNum());


        Button findButton = new Button(group1, SWT.NONE);
        Button saveButton = new Button(group1, SWT.NONE);
        Button deleteButton = new Button(group1, SWT.NONE);
        Button findAllButton = new Button(group1, SWT.NONE);
        Button editButton = new Button(group1, SWT.NONE);

        findButton.setText("查找");
        deleteButton.setText("删除");
        saveButton.setText("保存");
        findAllButton.setText("查找全部");
        editButton.setText("修改");
        saveButton.setBounds(120, 385, 94, 25);
        findButton.setBounds(40, 300, 70, 25);
        deleteButton.setBounds(40, 345, 70, 25);
        findAllButton.setBounds(120, 345, 94, 25);
        editButton.setBounds(40, 385,70,25);
        findAllButton.pack();

        Table table = new Table(group1, SWT.BORDER);
        table.setBounds(230, 20, 300, 350);
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

        Button addButton = new Button(group1, SWT.NONE);
        addButton.setBounds(120, 300, 94, 20);
        addButton.setText("添加");
        Label nLabel = new Label(group1, SWT.NONE);
        nLabel.setText("姓名:");
        nLabel.setBounds(20, 20, 90, 20);
        final Text name = new Text(group1, SWT.NONE);
        name.setBounds(120, 20, 90, 20);
        Label gLabel = new Label(group1, SWT.NONE);
        gLabel.setText("性别:");
        gLabel.setBounds(20, 50, 90, 20);
        Combo gender = new Combo(group1, SWT.DROP_DOWN | SWT.READ_ONLY);
        gender.setItems("男", "女");
        gender.setBounds(116, 50, 100, 20);
        Label iLabel = new Label(group1, SWT.NONE);
        iLabel.setText("工号:");
        iLabel.setBounds(20, 80, 90, 20);
        final Text id = new Text(group1, SWT.NONE);
        id.setBounds(120, 80, 90, 20);
        Label bLabel = new Label(group1, SWT.NONE);
        bLabel.setText("部门:");
        bLabel.setBounds(20, 110, 90, 20);
        Text branch = new Text(group1, SWT.READ_ONLY);
        branch.setBounds(120, 110, 90, 20);
        Label rLabel = new Label(group1, SWT.NONE);
        rLabel.setText("级别:");
        rLabel.setBounds(20, 140, 90, 20);
        Combo rank = new Combo(group1, SWT.NONE);
        rank.setBounds(117, 140, 100, 30);
        Label sLabel = new Label(group1, SWT.NONE);
        sLabel.setText("工资:");
        sLabel.setBounds(20, 170, 90, 20);
        final Text salary = new Text(group1, SWT.NONE);
        salary.setBounds(120, 170, 90, 20);
        Label pLabel = new Label(group1, SWT.NONE);
        pLabel.setText("密码:");
        pLabel.setBounds(20, 200, 90, 20);
        Text password = new Text(group1, SWT.PASSWORD);
        password.setBounds(120, 200, 90, 20);
        Label npLabel = new Label(group1, SWT.NONE);
        npLabel.setText("再次确认密码:");
        npLabel.setBounds(20, 230, 120, 20);
        Text nPassword = new Text(group1, SWT.PASSWORD);
        nPassword.setBounds(120, 230, 90, 20);
        Label eLabel = new Label(group1, SWT.NONE);
        eLabel.setBounds(220, 230, 120, 20);


        /* 只能输入整数 */
        id.addVerifyListener(e -> e.doit = "0123456789".contains(e.text));
        //salary.addVerifyListener(ee -> ee.doit = "0123456789".contains(ee.text));
        System.err.println("Main.java.304");

        findButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                String tName = name.getText();
                String tId = id.getText();
                ArrayList<Empolyee> findList = new ArrayList<>();
                if (tId.length() != 0) {
                    findList.add(hr.find(Integer.parseInt(tId)));
                    Func.log("find " + tId);
                } else if(tName.length() != 0){
                    findList = hr.find(tName);
                    Func.log("find " + tName);
                }
                if (findList.size() == 0) {
                    MessageBox msg = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.YES );
                    msg.setMessage("查无此人！\n" + ((tName.length() == 0)?(" "):("姓名:" + tName)) +
                            ((tId.length() == 0)?(" "):(" 工号:" + tId)));
                    msg.open();
                } else {
                    table.removeAll();
                    for(Empolyee empolyee : findList) {
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
                Iterator iterator = hr.getIter().iterator();
                while (iterator.hasNext()) {
                    Empolyee empolyee = (Empolyee) iterator.next();
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setText(new String[]{empolyee.getName(), empolyee.getGender(),String.valueOf(empolyee.getId()),
                            empolyee.getBranch(), empolyee.getRank(), String.valueOf(empolyee.getSalary())});
                }
            }
        });
        deleteButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelectionCount() == 0) return;
                MessageBox msg = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
                TableItem t = table.getItem(table.getSelectionIndex());
                msg.setMessage("是否要删除 " + t.getText());
                int rc = msg.open();
                if (rc == SWT.YES) {
                    Func.log("delete " + t.getText() + " " + t.getText(2));
                    hr.delete(Integer.parseInt(t.getText(2)));
                    table.remove(table.getSelectionIndex());
                }
            }
        });
        saveButton.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent e){
                try {
                    hr.saveFile(filePath + "output.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(table.getSelectionCount() == 0) return;
                TableItem t = table.getItem(table.getSelectionIndex());
                name.setText(t.getText(0));
                gender.setText(t.getText(1));
                id.setText(t.getText(2));
                branch.setText(t.getText(3));
                rank.setText(t.getText(4));
                salary.setText(t.getText(5));
            }
        });
        final boolean[] modifiedBranch = {false};
        final boolean[] modifiedRank = {false};
        final boolean[] modifiedSalary = {false};
        final boolean[] modifiedPassword = {false};

        branch.addModifyListener(modifyEvent -> modifiedBranch[0] = true);
        rank.addModifyListener(modifyEvent -> modifiedRank[0] = true);
        salary.addModifyListener(modifyEvent -> modifiedSalary[0] = true);
        password.addModifyListener(modifyEvent -> modifiedPassword[0] = true);

        editButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelectionCount() == 0) return;

                MessageBox msg = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
                TableItem t = table.getItem(table.getSelectionIndex());
                msg.setMessage("是否要修改 " + t.getText());
                Func.log("edit " + t.getText() + " " + t.getText(2));
                int id = Integer.parseInt(t.getText(2));
                if (modifiedBranch[0])  hr.modifyBranch(id, branch.getText());
                if (modifiedRank[0])    hr.modifyRank(id, rank.getText());
                if (modifiedSalary[0])  hr.modifySalary(id, Integer.parseInt(salary.getText()));
                if (modifiedPassword[0])hr.modifyPass(id, password.getText());

                //table.remove(table.getSelectionIndex());
            }
        });



        nPassword.addModifyListener(modifyEvent -> {
            if (password.getText().length() == 0) {
                eLabel.setText("密码不能为空");
            } else if(!password.getText().equals(nPassword.getText())) {
                eLabel.setText("两次输入的密码不一致");
                //eLabel.setForeground(new Color(255,0,0));
            } else {
                eLabel.setText("密码相同");
            }
        });

        Combo combo = new Combo(group1, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setItems("经理", "销售经理", "销售人员", "技术人员");
        combo.setBounds(116, 270, 100, 30);
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
                if (name.getText().length() == 0) {
                    npLabel.setText("姓名不能为空！");
                } else if (id.getText().length() == 0) {
                    npLabel.setText("工号不能为空！");
                } else if (salary.getText().length() == 0) {
                    npLabel.setText("工资不能为空！");
                } else if (!password.getText().equals(nPassword.getText())) {
                    npLabel.setText("两次输入的密码不相等！");
                } else if (password.getText().length() == 0) {
                    npLabel.setText("密码不能为空！");
                } else {
                    String info =  name.getText() + " " + gender.getItem(gender.getSelectionIndex()) + " " + id.getText()
                            + " " + branch.getText() + " " +
                            rank.getText() + " " + salary.getText() + " " + password.getText();
                    boolean p = hr.add(info);
                    MessageBox msg = new MessageBox(mainShell, SWT.YES);
                    if (p) {
                        msg.setMessage("添加成功！");
                        Func.log("add " + info);
                    } else {
                        msg.setMessage("工号重复！");
                    }
                    msg.open();
                }
            }
        });

        tabEdit.setControl(group1);
        tabCount.setControl(group3);
    }
}
