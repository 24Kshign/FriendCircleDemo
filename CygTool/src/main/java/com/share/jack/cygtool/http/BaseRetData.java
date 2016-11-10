package com.share.jack.cygtool.http;

/**
 * Gson返回Ret基本格式
 * 成功:code=1 + 业务数据 + 成功msg
 * 失败:code=0 + 业务数据 + 失败msg
 */
public class BaseRetData {
    private int code;         //成功1 失败0
    private String msg;  //错误msg

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return code != ConstantCode.REQUEST_SUCCESS;
    }
}
