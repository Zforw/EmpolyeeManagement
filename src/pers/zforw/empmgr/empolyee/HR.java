package pers.zforw.empmgr.empolyee;

import javax.swing.plaf.IconUIResource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 8:08 下午
 * @project: Basic
 * @description: 员工管理类，实现对员工的添加，查询，编辑，删除，统计
 */
public class HR {

    private int cnt;
    ArrayList<Empolyee> emp = new ArrayList<>(1024);
    /**
     * @description:
     * @param: [filePath] 数据文件目录
     * @return: void
     */
    public HR(String[] buf, int count) {
        cnt = count;
        for(int i = 1; i <= count;i++) {
            add(buf[i]);
        }
    }
    public int find(String name) {
        int pos = -1, i = 0;
        for(Empolyee e : emp) {
            if(e.getName().equals(name)) {
                pos = i;
            }
            i++;
        }
        return pos;
    }
    public int find(int id) {
        int pos = -1, i = 0;
        for(Empolyee e : emp) {
            if(e.getId() == id && e.isAtPost()) {
                pos = i;
            }
            i++;
        }
        return pos;
    }
    public boolean add(String info) {
        Empolyee e = new Empolyee(info);
        if(find(e.getId()) != -1)
            return false;
        emp.add(e);
        return true;
    }
    public boolean delete(int pos) {
        if(pos == -1) {
            return false;
        }
        emp.remove(pos);
        return true;
    }
    public boolean modifySaly(int pos, int salary) {
        emp.get(pos).setSalary(salary);
        return true;
    }
    public boolean modifyPass(int pos, String password) {
        emp.get(pos).setPassword(password);
        return true;
    }
    public boolean modifyBra(int pos, String branch) {
        emp.get(pos).setBranch(branch);
        return true;
    }
    public boolean modifyRank(int pos, String rank) {
        emp.get(pos).setRank(rank);
        return true;
    }
    public String[] save() {
        String[] str = new String[1024];
        int i = 0;
        for(Empolyee e : emp) {
            str[i++] = e.toString();
        }
        return str;
    }

    public void getNum() {
        System.out.println(Empolyee.getMale() + "," + Empolyee.getMale());
    }
}
