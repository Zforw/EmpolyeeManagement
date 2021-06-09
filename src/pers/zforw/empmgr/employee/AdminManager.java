package pers.zforw.empmgr.employee;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/29 10:33 下午
 * @project: Basic
 * @description:
 *      可以修改、删除任何人，不可修改密码
 */
public class AdminManager extends HR implements Authority {

    @Override
    public Employee delete(int id) {
        if (emp.get(id).getRank().equals("经理"))
            return null;
        return super.delete(id);
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public boolean modify() {
        return true;
    }

    @Override
    public boolean passwd() {
        return true;
    }

    @Override
    public boolean mdPass() {
        return false;
    }

    @Override
    public boolean addEmp() {
        return true;
    }

    @Override
    public boolean saveFe() {
        return true;
    }

    @Override
    public String auName() {
        return "Manager";
    }

}
