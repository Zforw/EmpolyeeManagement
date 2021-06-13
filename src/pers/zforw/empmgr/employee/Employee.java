package pers.zforw.empmgr.employee;

import pers.zforw.empmgr.main.Func;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:32 下午
 * @project: Basic
 * @description:
 */
public class Employee {
    private static int tot = 0;
    static protected int male = 0;
    static protected int female = 0;
    private final int id;
    private int salary;
    private String password;
    private final String name;
    private final String gender;
    private final String branch;
    private String rank;

    public static int getSelect(String r) {
        return switch (r) {
            case "P5" -> 1;
            case "P6" -> 2;
            case "P7" -> 3;
            default -> 0;
        };
    }

    public Employee(String info) {
        String[] array = Func.Split(info);
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

    protected String getInfo() {
        return String.format("%s %s %s %s %s %s %s", name, gender, id, branch, rank, salary, password);
    }

    protected void setSalary(int salary) {
        this.salary = salary;
        HR.Status = "update";
    }

    protected void setPassword(String password) {
        this.password = password;
        HR.Status = "update";
    }

    protected void setRank(String rank) {
        this.rank = rank;
        HR.Status = "update";
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

    protected Employee delete() {
        tot--;
        return this;
    };

}
