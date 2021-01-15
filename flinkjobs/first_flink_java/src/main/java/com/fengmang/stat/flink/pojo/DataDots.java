package com.fengmang.stat.flink.pojo;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2021/1/14 22:47
 */
public class DataDots {

    private String dName;
    private Integer dDots;
    private Long ts;

    public DataDots() {}

    public DataDots(String dName, Integer dDots, Long ts) {
        this.dName = dName;
        this.dDots = dDots;
        this.ts = ts;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Integer getdDots() {
        return dDots;
    }

    public void setdDots(Integer dDots) {
        this.dDots = dDots;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "DateDots{" +
                "dName='" + dName + '\'' +
                ", dDots=" + dDots +
                ", ts=" + ts +
                '}';
    }
}
