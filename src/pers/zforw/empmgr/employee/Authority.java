package pers.zforw.empmgr.employee;

public interface Authority {
    public int auth = 233;
    public abstract boolean delete();
    public abstract boolean modify();
    public abstract boolean passwd();
    public abstract boolean mdPass();
    public abstract boolean addEmp();
    public abstract boolean saveFe();
    public abstract String getName();
}
