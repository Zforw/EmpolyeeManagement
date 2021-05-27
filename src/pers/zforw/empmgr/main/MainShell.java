package pers.zforw.empmgr.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import pers.zforw.empmgr.employee.Employee;
import pers.zforw.empmgr.employee.HR;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/27 7:07 下午
 * @project: Basic
 * @description:
 */
public class MainShell {
    protected static void initMainShell() {
        TabFolder folder = new TabFolder(Main.mainShell, SWT.TOP);
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
        Button addButton = new Button(group1, SWT.NONE);

        findButton.setText("查找");
        deleteButton.setText("删除");
        saveButton.setText("保存");
        findAllButton.setText("查找全部");
        editButton.setText("修改");
        addButton.setText("添加");
        saveButton.setBounds(120, 390, 94, 25);
        findButton.setBounds(20, 300, 70, 25);
        deleteButton.setBounds(20, 345, 70, 25);
        findAllButton.setBounds(120, 345, 94, 25);
        editButton.setBounds(20, 390,70,25);
        addButton.setBounds(120, 300, 94, 25);


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
        eLabel.setBounds(10, 270, 120, 20);

        /* 只能输入整数 */
        /*
            id.addVerifyListener(e -> e.do it = "0123456789".contains(e.text));
            salary.addVerifyListener(e -> e.do it = "0123456789".contains(ee.text));
        */

        findButton.addSelectionListener(new SelectionAdapter(){
                @Override
                public void widgetSelected(SelectionEvent e) {
                    String tName = name.getText();
                    String tId = id.getText();
                    ArrayList<Employee> findList = new ArrayList<>();
                    if (tId.length() != 0) {
                        Employee emm = Main.hr.find(Integer.parseInt(tId));
                        if(emm != null) {
                            findList.add(emm);
                        }
                        Func.log("find " + tId);
                    } else if(tName.length() != 0){
                        findList = Main.hr.find(tName);
                        Func.log("find " + tName);
                    }
                    if (findList.size() == 0) {
                        MessageBox msg = new MessageBox(Main.mainShell, SWT.ICON_WARNING | SWT.YES );
                        msg.setMessage("查无此人！\n" + ((tName.length() == 0)?(" "):("姓名:" + tName)) +
                                ((tId.length() == 0)?(" "):(" 工号:" + tId)));
                        msg.open();
                    } else {
                        table.removeAll();
                        for(Employee employee : findList) {
                            TableItem item = new TableItem(table, SWT.NONE);
                            item.setText(new String[]{employee.getName(), employee.getGender(),String.valueOf(employee.getId()),
                                    employee.getBranch(), employee.getRank(), String.valueOf(employee.getSalary())});
                        }
                    }
                }
        });
        findAllButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                table.removeAll();
                for (Object o : Main.hr.getIter()) {
                    Employee employee = (Employee) o;
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setText(new String[]{employee.getName(), employee.getGender(), String.valueOf(employee.getId()),
                            employee.getBranch(), employee.getRank(), String.valueOf(employee.getSalary())});
                }
            }
        });
        deleteButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelectionCount() == 0) return;
                MessageBox msg = new MessageBox(Main.mainShell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
                TableItem t = table.getItem(table.getSelectionIndex());
                msg.setMessage("是否要删除 " + t.getText());
                int rc = msg.open();
                if (rc == SWT.YES) {
                    Func.log("delete " + t.getText() + " " + t.getText(2));
                    Main.hr.delete(Integer.parseInt(t.getText(2)));
                    table.remove(table.getSelectionIndex());
                }
            }
        });
        saveButton.addSelectionListener(new SelectionAdapter(){
            public void widgetSelected(SelectionEvent e){
                try {
                    Main.hr.saveFile(Main.filePath + "output.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        /*
         * @description: 
         * @param: []
         * @return: 
         */
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

        final boolean[] modifiedBranch   = {false};
        final boolean[] modifiedRank     = {false};
        final boolean[] modifiedSalary   = {false};
        final boolean[] modifiedPassword = {false};

        branch.addModifyListener(modifyEvent -> modifiedBranch[0] = true);
        rank.addModifyListener(modifyEvent -> modifiedRank[0] = true);
        salary.addModifyListener(modifyEvent -> modifiedSalary[0] = true);
        password.addModifyListener(modifyEvent -> modifiedPassword[0] = true);

        /*
         * @description: 
         * @param: []
         * @return: 
         */
        editButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelectionCount() == 0) return;
                MessageBox msg = new MessageBox(Main.mainShell, SWT.ICON_WARNING | SWT.NO | SWT.YES);
                TableItem t = table.getItem(table.getSelectionIndex());
                msg.setMessage("是否要修改 " + t.getText());
                Func.log("edit " + t.getText() + " " + t.getText(2));
                int id = Integer.parseInt(t.getText(2));
                if (modifiedBranch[0])   Main.hr.modifyBranch(id, branch.getText());
                if (modifiedRank[0])     Main.hr.modifyRank(id, rank.getText());
                if (modifiedSalary[0])   Main.hr.modifySalary(id, Integer.parseInt(salary.getText()));
                if (modifiedPassword[0]) Main.hr.modifyPass(id, password.getText());
                //table.remove(table.getSelectionIndex());
            }
        });

        nPassword.addModifyListener(modifyEvent -> {
            if (password.getText().length() == 0) {
                eLabel.setText("密码不能为空");
            } else if(!password.getText().equals(nPassword.getText())) {
                eLabel.setText("两次密码不一致");
                //eLabel.setForeground(new Color(255,0,0));
            } else {
                eLabel.setText("密码相同");
            }
        });

        Combo position = new Combo(group1, SWT.DROP_DOWN | SWT.READ_ONLY);
        position.setItems("经理", "销售经理", "销售人员", "技术人员");
        position.setBounds(124, 270, 90, 30);
        position.select(0);
        position.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (position.getSelectionIndex() == 0) {
                    branch.setText("管理");
                    rank.setText("经理");
                } else if (position.getSelectionIndex() == 1) {
                    branch.setText("销售");
                    rank.setText("经理");
                } else if (position.getSelectionIndex() == 2) {
                    branch.setText("销售");
                    rank.setText("职员");
                } else {
                    branch.setText("开发");
                    rank.setItems("P4", "P5", "P6", "P7");
                }
            }
        });
        addButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (name.getText().length() == 0) {
                    eLabel.setText("姓名不能为空！");
                } else if (id.getText().length() == 0) {
                    eLabel.setText("工号不能为空！");
                } else if (salary.getText().length() == 0) {
                    eLabel.setText("工资不能为空！");
                } else if (!password.getText().equals(nPassword.getText())) {
                    eLabel.setText("两次输入的密码不相等！");
                } else if (password.getText().length() == 0) {
                    eLabel.setText("密码不能为空！");
                } else {
                    String info =  name.getText() + " " + gender.getItem(gender.getSelectionIndex()) + " " + id.getText()
                            + " " + branch.getText() + " " +
                            rank.getText() + " " + salary.getText() + " " + password.getText();
                    boolean p = Main.hr.add(info);
                    MessageBox msg = new MessageBox(Main.mainShell, SWT.YES);
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
