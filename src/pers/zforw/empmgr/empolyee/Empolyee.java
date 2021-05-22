package pers.zforw.empmgr.empolyee;

import org.jetbrains.annotations.NotNull;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:32 下午
 * @project: Basic
 * @description:
 */
public class Empolyee {
    static protected int male = 0;
    static protected int female = 0;
    protected int id;
    protected int salary;
    protected boolean atPost;
    protected String password;
    protected String name;
    protected String gender;
    protected String branch;
    protected String rank;

    public Empolyee(String name, @NotNull String gender, int id, String branch, String rank, int salary, String password) {
        this.name = name;
        this.gender = gender;
        this.id = id;
        this.branch = branch;
        this.rank = rank;
        this.salary = salary;
        this.password = password;
        this.atPost = true;
        if (gender.equals("男")) {
            male++;
        } else {
            female++;
        }
    }
    public Empolyee(String info) {
        String[] array = info.split(" ");
        for(int i = 0; i < array.length;i++) {
            /**
             *  避免出现多个空格的情况
             */
            if(array[i].length() == 0 && i < array.length - 1) {
                String t = array[i];
                array[i] = array[i + 1];
                array[i + 1] = t;
            }
        }
        this.atPost = true;
        this.name = array[0];
        this.gender = array[1];
        if (gender.equals("男")) {
            male++;
        } else {
            female++;
        }
        this.id = Integer.parseInt(array[2]);
        this.branch = array[3];
        this.rank = array[4];
        this.salary = Integer.parseInt(array[5]);
        this.password = array[6];
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s", name, gender, id, branch, rank, salary, password);
    }
    /**
     * @description: 
     * @param: [info]
     * @return: 
     */

    public String getInfo() {
        return String.format("姓名:%s,性别:%s,ID:%s,部门:%s,级别:%s,薪资:%s", name, gender, id, branch, rank, salary);
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    public static int getMale() {
        return male;
    }

    public static int getFemale() {
        return female;
    }

    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBranch() {
        return branch;
    }

    public String getRank() {
        return rank;
    }

    public boolean isAtPost() {
        return atPost;
    }

    public void expel() {
        this.atPost = false;
    }

}
