package pers.zforw.empmgr.empolyee;

import pers.zforw.empmgr.main.SysLog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
    ArrayList<Empolyee> emp = new ArrayList<>(1024);
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
            if(e.getName().equals(name) && e.isAtPost()) {
                list.add(i);
            }
            i++;
        }
        return list.size() > 0?list:null;
    }
    public int find(int id) {
        int i = 0;
        for(Empolyee e : emp) {
            if (e.getId() == id && e.isAtPost()) {
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
            if((e.getId() == id || e.getName().equals(name)) && e.isAtPost()) {
                list.add(i);
            }
            i++;
        }
        return list.size() > 0?list:null;
    }
    public boolean add(String info) {
        Empolyee e;
        String[] args = Split(info);
        if(find(Integer.parseInt(args[2])) != -1)
            return false;
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
        emp.sort((o1, o2) -> (o1.getId() < o2.getId()) ? -1 : 1);
        return true;
    }
    public boolean delete(int pos) {
        if(pos == -1) {
            return false;
        }
        emp.remove(pos);
        size--;
        return true;
    }
    public int getSize() {
        return size;
    }

    public Empolyee get(int p) {
        return emp.get(p);
    }

    public String[] openFile(String filePath) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String[] bufString = new String[1024];
            String line;
            size = 0;
            root = Split(br.readLine());
            while ((line = br.readLine()) != null) {
                bufString[size++] = line;
            }
            br.close();
            for (int i = 0; i < size; i++) {
                add(bufString[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            SysLog.log("data.txt File not found.");
        }
        return root;
    }
    public void saveFile(String fileName) {
        try {
            OutputStream os = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(os);
            pw.println(root[0] + " " + root[1]);
            String[] buf = new String[1024];
            int i = 0;
            for(Empolyee e : emp) {
                buf[i++] = e.toString();
            }
            for (i = 0; i < size; i++) {
                pw.println(buf[i]);
            }
            pw.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getNum() {
        return String.format("共有: %d 人, %d 男, %d 女\n经理: %d 人, 销售经理: %d 人\n技术人员: %d 人, 销售人员: %d 人",
                Empolyee.getTot(), Empolyee.getMale(), Empolyee.getFemale(),
                Manager.getTotal(), SalesManager.getTotal(), Technician.getTotal(), SalesClerk.getTotal());
    }
    /**
     * @description: 用空格将字符串分割
     * @param: [str]
     * @return:
     */
    public static String[] Split(String str) {
        String[] result = str.split(" ");
        for (int i = 0; i < result.length; i++) {
            /* 当两个子字符串之间多于一个空格的情况 */
            if (result[i].length() == 0 && i < result.length - 1) {
                String t = result[i];
                result[i] = result[i + 1];
                result[i + 1] = t;
            }
        }
        return result;
    }
}
