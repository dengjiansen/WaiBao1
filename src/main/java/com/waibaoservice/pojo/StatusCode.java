package com.waibaoservice.pojo;

/**
 * @author DJS
 * Date create 0:52 2023/3/11
 * Modified By DJS
 **/
public class StatusCode {
   private Integer errcode;
   private String errmsg;
   private Long msgid;

    public Long getMsgid() {
        return msgid;
    }

    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
