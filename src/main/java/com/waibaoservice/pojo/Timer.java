package com.waibaoservice.pojo;

/**
 * @author DJS
 * Date create 0:08 2023/3/10
 * Modified By DJS
 **/
public class Timer {
    private String openid;
    private String unionid;
    private String end_time;
    private String positive_time;
    private String device_id;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPositive_time() {
        return positive_time;
    }

    public void setPositive_time(String positive_time) {
        this.positive_time = positive_time;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "Timer{" +
                "openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                ", end_time='" + end_time + '\'' +
                ", positive_time='" + positive_time + '\'' +
                '}';
    }
}
