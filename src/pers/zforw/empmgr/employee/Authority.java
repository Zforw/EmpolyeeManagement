package pers.zforw.empmgr.employee;

public interface Authority {
    public abstract boolean delete();
    public abstract boolean modify();
    public abstract boolean passwd();
    public abstract boolean addEmp();
}
