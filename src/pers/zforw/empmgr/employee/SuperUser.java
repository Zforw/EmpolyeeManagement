package pers.zforw.empmgr.employee;

import pers.zforw.empmgr.main.Func;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/05/27 8:52 下午
 * @project: Basic
 * @description:
 */
public class SuperUser extends HR implements Authority {

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
        return true;
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
    public boolean mdRank() {
        return true;
    }

    @Override
    public String auName() {
        return "SuperUser";
    }
}
