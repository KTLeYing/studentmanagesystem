package com.mzl.studentmanagesystem.util;

/**
 * @ClassName :   AjaxResult
 * @Description: ajax返回结果的封装
 * @Author: 21989
 * @CreateDate: 2020/7/29 15:31
 * @Version: 1.0
 */
public class AjaxResult {

    private boolean success;
    private String message;
    private String imgurl;
    private String type;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
