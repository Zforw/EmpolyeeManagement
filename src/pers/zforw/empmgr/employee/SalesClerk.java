package pers.zforw.empmgr.employee;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:47 下午
 * @project: Basic
 * @description:
 */
public class SalesClerk extends Employee {
    public final static String[] rank = {"职员"};
    private static int total = 0;

    public SalesClerk(String info) {
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
