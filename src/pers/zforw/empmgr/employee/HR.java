package pers.zforw.empmgr.employee;

import pers.zforw.empmgr.main.Func;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    protected int size;
    static protected String[] root;
    static protected TreeMap<Integer, Employee> emp = new TreeMap<>();
    public HR() {
        size = 0;
    }
    /**
     * @description:
     *      返回id对应的员工对象
     * @param: [id]
     * @return:
     */
    public Employee find(int id) {
        if (!emp.containsKey(id)) return null;
        return emp.get(id);
    }
    /*
     * @description: 返回和name相似度大于0.15的名字
     * @param: [name]
     * @return:
     */
    public ArrayList<Employee> find(String name) {
        Map<Float, Employee> map = new HashMap<>();
        for (Integer id : emp.keySet()) {
            float similarity = Func.levenshtein(emp.get(id).getName(), name);
            if ((similarity > 0.15)) {
                map.put(similarity, emp.get(id));
            }
        }
        List<Map.Entry<Float, Employee>> list = new ArrayList<>(map.entrySet());
        ArrayList<Employee> res = new ArrayList<>();
        for (Map.Entry<Float, Employee> e : list) {
            res.add(e.getValue());
        }
        return res;
    }
    /**
     * @description: 添加人员
     *      1.将信息进行分割
     *      2.查找是否存在
     *      3.根据人员信息选择不同的类
     *      4.添加对象
     * @param: [info]
     * @return: 是否添加成功
     */
    public boolean add(Employee employee) {
        System.out.println("given " + employee.getClass().getName());
        Employee e;
        if(find(employee.getId()) != null)
            return false;
        size++;
        String info = employee.getInfo();
        String branch = employee.getBranch();
        if(branch.equals("开发")) {
            e = new Technician(info);
        } else if(branch.equals("销售")) {
            if (employee.getRank().equals("经理")) {
                e = new SalesManager(info);
            } else {
                e = new SalesClerk(info);
            }
        } else {
            e = new Manager(info);
        }
        emp.put(employee.getId(), e);
        //emp.sort(Comparator.comparingInt(Employee::getId));
        return true;
    }
    public boolean add(String info) {
        Employee e;
        String[] args = Func.Split(info);
        if(find(Integer.parseInt(args[2])) != null)
            return false;
        size++;
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
        //emp.sort(Comparator.comparingInt(Employee::getId));
        return true;
    }
    public Employee delete(int id) {
        size--;
        return emp.remove(id);
    }

    public void modifyRank(int id, String rank) {
        emp.get(id).setRank(rank);
    }
    public void modifySalary(int id, int salary) {
        emp.get(id).setSalary(salary);
    }
    public void modifyPass(int id, String pass) {
        emp.get(id).setPassword(pass);
    }

    public Collection getIter() {
        return emp.values();
    }

    HR Check(String name, String pass) {
        HR h = null;
        if (name.equals(root[0]) && pass.equals(root[1]))
            h = new SuperUser();
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
                return h;
            }
        }
        return h;
    }

    /**
     * @description:
     * @param: [fileName]
     * @return:
     */
    public void loadFile(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        root = Func.Split(Func.decrypt(br.readLine()));
        while ((line = br.readLine()) != null) {
            add(Func.decrypt(line));
        }
        br.close();
    }
    /**
     * @description:
     * @param: [fileName]
     * @return:
     */
    public void saveFile(String fileName) throws IOException {
        OutputStream os = new FileOutputStream(fileName);
        PrintWriter pw = new PrintWriter(os);
        pw.println(Func.encrypt(root[0] + " " + root[1]));
        String[] buf = new String[size];
        int i = 0;
        for(Integer id : emp.keySet()) {
            buf[i++] = Func.encrypt(emp.get(id).getInfo());
        }
        for (i = 0; i < size; i++) {
            pw.println(buf[i]);
        }
        pw.close();
        os.close();
    }

    public static String getNum() {
        return String.format("共有: %d 人, %d 男, %d 女\n经理: %d 人, 销售经理: %d 人\n技术人员: %d 人, 销售人员: %d 人",
                Employee.getTot(), Employee.getMale(), Employee.getFemale(),
                Manager.getTotal(), SalesManager.getTotal(), Technician.getTotal(), SalesClerk.getTotal());
    }

}