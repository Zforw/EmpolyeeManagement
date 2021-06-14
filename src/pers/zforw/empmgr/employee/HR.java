package pers.zforw.empmgr.employee;
import pers.zforw.empmgr.main.Func;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 8:08 下午
 * @project: Basic
 * @description: 员工管理类，实现对员工的添加，查询，编辑，删除，统计
 */
public class HR {
    static final String DB_url = "jdbc:mysql://localhost:3306/EMPLOYEE";
    static final String username = "root";
    static final String password = "zfwixeon";

    protected static String Status = "insert";
    protected static TreeMap<Integer, Employee> emp = new TreeMap<>();
    protected static ArrayList<String> changelist = new ArrayList<>();
    protected static String[] root;
    public static Employee self;
    public static String name;

    public HR() {}
    /**
     * @description: 返回id对应的员工对象
     * @param: [id]
     * @return:
     */
    public Employee findById(int id) {
        if (!emp.containsKey(id)) return null;
        return emp.get(id);
    }
    /**
     * @description: 返回和name相似度大于0.3的名字
     * @param: [name]
     * @return:
     */
    public ArrayList<Employee> findByName(String name) {
        Map<Float, ArrayList<Employee>> map = new HashMap<>();
        for (Integer id : emp.keySet()) {
            ArrayList<Employee> list;
            float similarity = Func.levenshtein(emp.get(id).getName(), name);
            if ((similarity > 0.3)) {
                if (map.containsKey(similarity)) list = map.get(similarity);
                else list = new ArrayList<>();
                list.add(emp.get(id));
                map.put(similarity, list);
            }
        }
        ArrayList<Employee> res = new ArrayList<>();
        for (Map.Entry<Float, ArrayList<Employee>> e : map.entrySet()) {
            res.addAll(e.getValue());
        }
        return res;
    }
    /**
     * @description: 修改员工类别
     * @param: [employee, branch, rank, salary, password] [删除后返回的员工, 新的部门, 职位, 工资, 密码]
     * @return:
     */
    public void restore(Employee employee, String branch, String rank, String salary, String password) {
        String[] args = Func.Split(employee.getInfo());
        args[3] = branch;
        args[4] = rank;
        args[5] = salary;
        if (!password.isEmpty())args[6] = password;
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < 6;i++) {
            info.append(args[i]).append(" ");
        }
        info.append(args[6]);
        changelist.remove(changelist.size() - 1);
        changelist.add("update " + info);
        Employee e;
        if(branch.equals("开发")) {
            e = new Technician(info.toString());
        } else if(branch.equals("销售")) {
            if (employee.getRank().equals("经理")) {
                e = new SalesManager(info.toString());
            } else {
                e = new SalesClerk(info.toString());
            }
        } else {
            e = new Manager(info.toString());
        }
        emp.put(employee.getId(), e);
    }

    /**
     * @description:
     * @param: [info] 员工信息格式:[name, gender, id, branch, rank, salary, password]
     * @return:
     */
    public Employee add(String info) {
        Employee e;
        String[] args = Func.Split(info);
        if(findById(Integer.parseInt(args[2])) != null)
            return null;
        if (!Status.equals("init")) changelist.add(Status + " " + info);
        if(args[3].equals("开发")) {
            e = new Technician(info);
        } else if(args[3].equals("销售")) {
            if (args[4].equals("经理")) {
                e = new SalesManager(info);
            } else {
                e = new SalesClerk(info);
            }
        } else {
            e = new Manager(info);
        }
        emp.put(Integer.parseInt(args[2]), e);
        return e;
    }
    /**
     * @description: 删除员工
     *  1、HR size 减一,
     *  2、调用所在类的delete方法，总数减1，并将信息保存到changes
     *  3、返回删除的Employee
     * @param: [id]
     * @return:
     */
    public Employee delete(int id) {
        changelist.add("delete " + emp.get(id).delete().getInfo());
        return emp.remove(id);
    }
    public void modifyRank(int id, String rank) {
        Employee e = emp.get(id);
        e.setRank(rank);
        changelist.add("update " + e.getInfo());
    }
    public void modifySalary(int id, int salary) {
        Employee e = emp.get(id);
        e.setSalary(salary);
        changelist.add("update " + e.getInfo());
    }
    public void modifyPass(int id, String pass) {
        Employee e = emp.get(id);
        e.setPassword(pass);
        changelist.add("update " + e.getInfo());
    }

    public Collection getIter() {
        return emp.values();
    }
    public int getSize() { return emp.size(); }

    public static HR Check(String name, String pass) {
        HR h;
        if (name.equals(root[0]) && pass.equals(root[1])) {
            h = new SuperUser();
            HR.name = root[0];
            return h;
        }
        for (Integer id : emp.keySet()) {
            Employee employee = emp.get(id);
            if (employee.getName().equals(name) && employee.getPassword().equals(pass)) {
                if (employee.getBranch().equals("销售") && employee.getRank().equals("经理")) {
                    h = new AdminSales();
                } else if(employee.getRank().equals("经理")) {
                    h = new AdminManager();
                } else {
                    h = new User();
                }
                self = employee;
                HR.name = name;
                return h;
            }
        }
        return null;
    }
    /**
     * @description: 从数据库加载数据覆盖本地数据文件
     * @param: [fileName]
     * @return:
     */
    private void loadDatabase(String fileName) {
        try{
            OutputStream os = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(os);
            pw.println(Func.encrypt("su 123"));
            Class.forName("com.mysql.cj.jdbc.Driver");//1.注册JDBC驱动
            Connection connection = DriverManager.getConnection(DB_url, username, password);//2.获取数据库连接
            Statement statement = connection.createStatement();//3.操作数据库
            String sql = "select * from info";//定义数据库语句
            ResultSet resultSet = statement.executeQuery(sql);//执行数据库语句获取结果集
            while (resultSet.next()) {
                String info = resultSet.getString(1) + " " + resultSet.getString(2) +
                        " " + resultSet.getString(3) + " " + resultSet.getString(4) +
                        " " + resultSet.getString(5) + " " + resultSet.getString(6) +
                        " " + resultSet.getString(7);
                pw.println(Func.encrypt(info));
            }
            pw.close();
            os.close();
            resultSet.close();
            statement.close();
            connection.close();
            //4.关闭结果集，数据库操作对象，数据库连接
        }catch (ClassNotFoundException | IOException | SQLException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * @description: 从文件加载数据
     * @param: [fileName]
     * @return:
     */
    public void loadFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.createNewFile();
            Func.log("data file lost, create from database");
            root = new String[]{"su", "123"};
            loadDatabase(fileName);
        }
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        try {
            Status = "init";
            root = Func.Split(Func.decrypt(br.readLine()));
            while ((line = br.readLine()) != null) {
                add(Func.decrypt(line));
            }
            Status = "insert";
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | UnsupportedEncodingException exception) {
            //文件损坏则从数据库加载
            emp.clear();
            Func.log("data file damaged, create from database");
            loadDatabase(fileName);
            loadFile(fileName);
        }
        br.close();
    }
    /**
     * @description: 将数据保存到文件并更新数据库
     * @param: [fileName]
     * @return:
     */
    public void saveFile(String fileName) throws IOException, SQLException, ClassNotFoundException {
        if (changelist.size() == 0) return;//如果没有改变就直接退出
        OutputStream os = new FileOutputStream(fileName);
        PrintWriter pw = new PrintWriter(os);//覆盖原文件
        pw.println(Func.encrypt(root[0] + " " + root[1]));
        for(Integer id : emp.keySet()) {
            pw.println(Func.encrypt(emp.get(id).getInfo()));
        }
        pw.close();
        os.close();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_url, username, password);
            Statement statement = connection.createStatement();
            String sql;
            for (String s : changelist) {
                System.out.println(s);
                String[] inst = Func.Split(s);
                if (inst[0].equals("insert")) {
                    sql = "insert into info(a,b,c,d,e,f,g)value (?,?,?,?,?,?,?)";
                    PreparedStatement pStmt = connection.prepareStatement(sql);
                    pStmt.setString(1, inst[1]);
                    pStmt.setString(2, inst[2]);
                    pStmt.setString(3, inst[3]);
                    pStmt.setString(4, inst[4]);
                    pStmt.setString(5, inst[5]);
                    pStmt.setString(6, inst[6]);
                    pStmt.setString(7, inst[7]);
                    pStmt.executeUpdate();
                    pStmt.close();
                } else if (inst[0].equals("update")) {
                    sql = "update info set d=?, e=?, f=?, g=? where c = '" + inst[3] + "'";
                    PreparedStatement pStmt = connection.prepareStatement(sql);
                    pStmt.setString(1, inst[4]);
                    pStmt.setString(2, inst[5]);
                    pStmt.setString(3, inst[6]);
                    pStmt.setString(4, inst[7]);
                    pStmt.executeUpdate();
                    pStmt.close();
                } else {
                    sql = "delete from info where c='" + inst[3] + "'";
                    PreparedStatement pStmt = connection.prepareStatement(sql);
                    pStmt.executeUpdate();
                    pStmt.close();
                }
            }
            statement.close();
            connection.close();
            changelist.clear();//点击保存后清除之前的记录
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static String getNum() {
        return String.format("共有: %d 人, %d 男, %d 女\n经理: %d 人, 销售经理: %d 人\n技术人员: %d 人, 销售人员: %d 人",
                Employee.getTot(), Employee.getMale(), Employee.getFemale(),
                Manager.getTotal(), SalesManager.getTotal(), Technician.getTotal(), SalesClerk.getTotal());
    }

    public void modifyRootPass(String pass) {}
    /**
     * @description: 根据部门职位获取对应的人员全称
     * @param: [branch, rank]
     * @return:
     */
    public static String getPosition(String branch, String rank) {
        if (branch.equals("管理")) {
            return "经理";
        } else if (branch.equals("开发")) {
            return "技术人员";
        } else if (rank.equals("职员")) {
            return "销售人员";
        } else {
            return "销售经理";
        }
    }
}