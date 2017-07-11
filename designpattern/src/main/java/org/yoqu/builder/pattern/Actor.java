package org.yoqu.builder.pattern;

/**
 * @author yoqu
 * @date 2017年07月11日
 * @time 上午9:17
 * @email wcjiang2@iflytek.com
 */
public class Actor {
    private String face;//脸型
    private String hairstyle;//发型
    private String type;//类型
    private String sex;//性别

    @Override
    public String toString() {
        return "Actor{" +
                "face='" + face + '\'' +
                ", hairstyle='" + hairstyle + '\'' +
                ", type='" + type + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getHairstyle() {
        return hairstyle;
    }

    public void setHairstyle(String hairstyle) {
        this.hairstyle = hairstyle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
