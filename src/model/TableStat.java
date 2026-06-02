package model;

import java.io.Serializable;

public class TableStat implements Serializable {
    private static final long serialVersionUID = 1L;
    private Ban ban;
    private int tongLuotKhach;
    private float doanhThu;

    public TableStat() {
        super();
    }

    public TableStat(Ban ban, int tongLuotKhach, float doanhThu) {
        super();
        this.ban = ban;
        this.tongLuotKhach = tongLuotKhach;
        this.doanhThu = doanhThu;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public int getTongLuotKhach() {
        return tongLuotKhach;
    }

    public void setTongLuotKhach(int tongLuotKhach) {
        this.tongLuotKhach = tongLuotKhach;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }
}
