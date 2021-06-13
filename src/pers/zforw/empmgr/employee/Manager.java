package pers.zforw.empmgr.employee;


/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/21 7:44 下午
 * @project: Basic
 * @description:
 */
public class Manager extends Employee {
    public final static String[] rank = {"经理"};
    private static int total = 0;

    public Manager(String info) {
        super(info);
        total++;
    }
    public static int getTotal() {
        return total;
    }
    @Override
    public Employee delete() {
        total--;
        return super.delete();
    }
}
