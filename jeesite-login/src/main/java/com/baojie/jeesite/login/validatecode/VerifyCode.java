package com.baojie.jeesite.login.validatecode;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
public class VerifyCode {

    private String code;
    private byte[] imgBytes;
    private long expireTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

}
