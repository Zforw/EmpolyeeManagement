package pers.zforw.empmgr.empolyee;

import org.jetbrains.annotations.NotNull;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:44 下午
 * @project: Basic
 * @description:
 */
public class Manager extends Empolyee{
    private int total = 0;
    public Manager(String name, @NotNull String gender, int id, String branch, String rank, int salary, String password) {
        super(name, gender, id, branch, rank, salary, password);
        total++;
    }
    public int getTotal() {
        return total;
    }
}
