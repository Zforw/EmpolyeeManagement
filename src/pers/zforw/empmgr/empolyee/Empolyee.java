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
    private static int tot = 0;
    static protected int male = 0;
    static protected int female = 0;
    protected int id;
    protected int salary;
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
        tot++;
        if (gender.equals("男")) {
            male++;
        } else {
            female++;
        }
    }
    public Empolyee(String info) {
        String[] array = HR.Split(info);
        this.name = array[0];
        this.gender = array[1];
        if (gender.equals("男")) {
            male++;
        } else {
            female++;
        }
        tot++;
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


    public void setSalary(int salary) {
        this.salary = salary;
    }

    protected void setPassword(String password) {
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

    protected String getPassword() {
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

    public static int getTot() { return tot; }


}
