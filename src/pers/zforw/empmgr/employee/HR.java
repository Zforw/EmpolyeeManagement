package pers.zforw.empmgr.employee;
import pers.zforw.empmgr.main.Func;

import java.io.BufferedReader;
import java.io.File;
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
    protected static int size = 0;
    protected static TreeMap<Integer, Employee> emp = new TreeMap<>();
    public static String[] root;
    public static String[] self;
    public static String name;
    public HR() {

    }
    /**
     * @description:
     *      返回id对应的员工对象
     * @param: [id]
     * @return:
     */
    public Employee findById(int id) {
        if (!emp.containsKey(id)) return null;
        return emp.get(id);
    }
    /*
     * @description: 返回和name相似度大于0.15的名字
     * @param: [name]
     * @return:
     */
    public ArrayList<Employee> findByName(String name) {
        Map<Float, ArrayList<Employee>> map = new HashMap<>();
        for (Integer id : emp.keySet()) {
            ArrayList<Employee> list;
            float similarity = Func.levenshtein(emp.get(id).getName(), name);
            if ((similarity > 0.15)) {
                if (map.containsKey(similarity)) list = map.get(similarity);
                else list = new ArrayList<>();
                list.add(emp.get(id));
                map.put(similarity, list);
            }
        }
        List<Map.Entry<Float, ArrayList<Employee>>> list = new ArrayList<>(map.entrySet());
        ArrayList<Employee> res = new ArrayList<>();
        for (Map.Entry<Float, ArrayList<Employee>> e : list) {
            res.addAll(e.getValue());
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
    public void add(Employee employee) {
        System.out.println("given " + employee.getClass().getName());
        Employee e;
        if(findById(employee.getId()) != null)
            return;
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
    }
    public boolean add(String info) {
        Employee e;
        String[] args = Func.Split(info);
        if(findById(Integer.parseInt(args[2])) != null)
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
    public int getSize() { return size; }

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
                self = Func.Split(employee.getInfo());
                HR.name = name;
                return h;
            }
        }
        return null;
    }

    /**
     * @description:
     * @param: [fileName]
     * @return:
     */
    public void loadFile(String fileName) throws IOException{
        File file = new File(fileName);
        if(!file.exists()) {
            file.createNewFile();
            root = new String[]{"su", "123"};
            return;
        }
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
        for(Integer id : emp.keySet()) {
            pw.println(Func.encrypt(emp.get(id).getInfo()));
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