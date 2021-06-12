package pers.zforw.empmgr.employee;

import pers.zforw.empmgr.main.Func;

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
    public void restore(Employee employee, String branch, String rank, String salary, String password) {
        HR.size++;
        String[] args = Func.Split(employee.getInfo());
        args[3] = branch;
        args[4] = rank;
        args[5] = salary;
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < 6;i++) {
            info.append(args[i]).append(" ");
        }
        info.append(args[6]);
        HR.changes.remove(changes.size() - 1);
        HR.changes.add(Status + " " + info);
        Employee e;
        if(branch.equals("开发")) {
            e = new Technician(info.toString());
        } else if(branch.equals("销售")) {
            if (employee.getRank().equals("经理")) {
                e = new SalesManager(info.toString());
            } else {
                e = new SalesClerk(info.toString());
            }
        } else {
            e = new Manager(info.toString());
        }
        HR.emp.put(employee.getId(), e);
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
    public boolean mdRank() {
        return true;
    }

    @Override
    public String auName() {
        return "Manager";
    }

}
