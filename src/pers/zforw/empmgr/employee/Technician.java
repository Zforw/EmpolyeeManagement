package pers.zforw.empmgr.employee;

import org.jetbrains.annotations.NotNull;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:48 下午
 * @project: Basic
 * @description:
 */
public class Technician extends Employee {
    public final static String[] rank = {"P4", "P5", "P6", "P7"};
    private static int total = 0;
    public Technician(String name, @NotNull String gender, int id, String branch, String rank, int salary, String password) {
        super(name, gender, id, branch, rank, salary, password);
        total++;
    }

    public Technician(String info) {
        super(info);
        total++;
    }

    public static int getTotal() {
        return total;
    }
}
