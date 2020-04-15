package qicc.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;


public class ResultModel {

    @Excel(name = "部门",orderNum = "1",width = 50)
    private String replyStationName;

    @Excel(name = "操作人姓名",orderNum = "2",width = 25)
    private String replyPersonName;

    @Excel(name = "操作时间",orderNum = "3",exportFormat = "yyyy-MM-dd HH:mm:ss",width = 30)
    private Date replyTime;

    @Excel(name = "操作详情",orderNum = "4",width = 100)
    private String replyContent;

    public String getReplyStationName() {
        return replyStationName;
    }

    public void setReplyStationName(String replyStationName) {
        this.replyStationName = replyStationName;
    }

    public String getReplyPersonName() {
        return replyPersonName;
    }

    public void setReplyPersonName(String replyPersonName) {
        this.replyPersonName = replyPersonName;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public ResultModel(String replyStationName, String replyPersonName, Date replyTime, String replyContent) {
        this.replyStationName = replyStationName;
        this.replyPersonName = replyPersonName;
        this.replyTime = replyTime;
        this.replyContent = replyContent;
    }

    public ResultModel() {
    }
}
