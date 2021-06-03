package pers.zforw.empmgr.employee;

public interface Authority {
    boolean delete();
    boolean modify();
    boolean passwd();
    boolean mdPass();
    boolean addEmp();
    boolean saveFe();
    String auName();
}
