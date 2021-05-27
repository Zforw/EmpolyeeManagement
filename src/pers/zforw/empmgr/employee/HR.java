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

    private int size;
    static private String[] root;
    TreeMap<Integer, Employee> emp = new TreeMap<>();
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
     * @description: 返回和name相似度大于0.15的
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
                e = new SalesClerk(info);
            } else {
                e = new SalesManager(info);
            }
        } else {
            e = new Manager(info);
        }
        emp.put(Integer.parseInt(args[2]), e);
        //emp.sort(Comparator.comparingInt(Employee::getId));
        return true;
    }
    public void delete(int id) {
        emp.remove(id);
        size--;
    }
    public int getSize() {
        return size;
    }
    public void modifyRank(int id, String rank) {
        emp.get(id).setRank(rank);
    }
    public void modifyBranch(int id, String branch) {
        emp.get(id).setBranch(branch);
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


    /**
     * @description:
     * @param: [filePath]
     * @return:
     */
    public String[] loadFile(String filePath) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        root = Func.Split(Func.decrypt(br.readLine()));
        while ((line = br.readLine()) != null) {
            add(Func.decrypt(line));
        }
        br.close();
        return root;
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
            buf[i++] = Func.encrypt(emp.get(id).toString());
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