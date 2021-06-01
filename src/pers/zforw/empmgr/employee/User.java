package pers.zforw.empmgr.employee;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/29 11:07 下午
 * @project: Basic
 * @description:
 */
public class User extends HR implements Authority {
    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean modify() {
        return false;
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
        return false;
    }

    @Override
    public String auName() {
        return "User";
    }
}
