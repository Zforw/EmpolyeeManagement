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

    public Technician(String info) {
        super(info);
        total++;
    }

    public static int getTotal() {
        return total;
    }
    public Employee delete() {
        total--;
        return super.delete();
    }
}
