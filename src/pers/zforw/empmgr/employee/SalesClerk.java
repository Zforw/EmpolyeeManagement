package pers.zforw.empmgr.employee;

import org.jetbrains.annotations.NotNull;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:47 下午
 * @project: Basic
 * @description:
 */
public class SalesClerk extends Employee {
    private static int total = 0;
    public SalesClerk(String name, @NotNull String gender, int id, String branch, String rank, int salary, String password) {
        super(name, gender, id, branch, rank, salary, password);
        total++;
    }

    public SalesClerk(String info) {
        super(info);
        total++;
    }
    public static int getTotal() {
        return total;
    }
}
