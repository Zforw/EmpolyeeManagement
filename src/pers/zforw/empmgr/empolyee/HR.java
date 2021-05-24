package pers.zforw.empmgr.empolyee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

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
    ArrayList<Empolyee> emp = new ArrayList<>();
    public HR(ArrayList<Empolyee> e) {
        emp = e;
        size = e.size();
    }
    public HR() {
        size = 0;
    }
    /**
     * @description: 通过姓名查找
     * @param: [name]
     * @return:
     */
    public ArrayList<Integer> find(String name) {
        int i = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for(Empolyee e : emp) {
            if(e.getName().equals(name)) {
                list.add(i);
            }
            i++;
        }
        return list.size() > 0?list:null;
    }
    /**
     * @description:
     *      返回id所在emp内的位置，-1为不存在
     * @param: [id]
     * @return:
     */
    public int find(int id) {
        int i = 0;
        for(Empolyee e : emp) {
            if (e.getId() == id) {
                return i;
            }
            i++;
        }
        return -1;
    }
    public ArrayList<Integer> find(String name, int id) {
        int i = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for(Empolyee e : emp) {
            if((e.getId() == id || e.getName().equals(name))) {
                list.add(i);
            }
            i++;
        }
        return list.size() > 0?list:null;
    }
    /**
     * @description: 添加人员
     *      1.将信息进行分割
     *      2.查找是否存在，-1：不存在
     *      3.根据人员信息选择不同的类
     *      4.添加对象
     *      5.按照ID排序
     * @param: [info]
     * @return: 是否添加成功
     */
    public boolean add(String info) {
        Empolyee e;
        String[] args = Split(info);
        if(find(Integer.parseInt(args[2])) != -1)
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
        emp.add(e);
        emp.sort(Comparator.comparingInt(Empolyee::getId));
        return true;
    }
    public void delete(int pos) {
        emp.remove(pos);
        size--;
    }
    public int getSize() {
        return size;
    }
    public Empolyee get(int p) {
        return emp.get(p);
    }

    /**
     * @description:
     * @param: [filePath]
     * @return:
     */
    public String[] openFile(String filePath) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        root = Split(br.readLine());
        while ((line = br.readLine()) != null) {
            add(line);
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
        pw.println(root[0] + " " + root[1]);
        String[] buf = new String[size];
        int i = 0;
        for(Empolyee e : emp) {
            buf[i++] = e.toString();
        }
        for (i = 0; i < size; i++) {
            pw.println(buf[i]);
        }
        pw.close();
        os.close();
    }
    public static String getNum() {
        return String.format("共有: %d 人, %d 男, %d 女\n经理: %d 人, 销售经理: %d 人\n技术人员: %d 人, 销售人员: %d 人",
                Empolyee.getTot(), Empolyee.getMale(), Empolyee.getFemale(),
                Manager.getTotal(), SalesManager.getTotal(), Technician.getTotal(), SalesClerk.getTotal());
    }
    /**
     * @description: 用空格将字符串分割
     *      用于处理当两个子字符串之间多于一个空格的特殊情况
     * @param: [str]
     * @return:
     */
    public static String[] Split(String str) {
        String[] result = str.split(" ");
        for (int i = 0; i < result.length; i++) {
            /*  */
            if (result[i].length() == 0 && i < result.length - 1) {
                String t = result[i];
                result[i] = result[i + 1];
                result[i + 1] = t;
            }
        }
        return result;
    }
}
