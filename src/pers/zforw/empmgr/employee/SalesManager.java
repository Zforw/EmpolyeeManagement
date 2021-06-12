package pers.zforw.empmgr.employee;

import org.jetbrains.annotations.NotNull;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:49 下午
 * @project: Basic
 * @description:
 */
public class SalesManager extends Employee {
    public final static String[] rank = {"经理"};
    private static int total = 0;

    public SalesManager(String info) {
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
