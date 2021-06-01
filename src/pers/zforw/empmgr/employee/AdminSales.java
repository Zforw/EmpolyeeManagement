package pers.zforw.empmgr.employee;

import java.util.TreeMap;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/29 10:34 下午
 * @project: Basic
 * @description:
 *      只能修改、删除销售人员，不能修改密码
 */
public class AdminSales extends HR implements Authority {

    @Override
    public Employee delete(int id) {
        if (!emp.get(id).getRank().equals("职员"))
            return null;
        return super.delete(id);
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean modify() {
        return true;
    }

    @Override
    public boolean passwd() {
        return false;
    }

    @Override
    public boolean mdPass() {
        return false;
    }

    @Override
    public boolean addEmp() {
        return false;
    }

    @Override
    public boolean saveFe() {
        return true;
    }

    @Override
    public String auName() {
        return "SalesManager";
    }
}
