package org.yoqu.builder.pattern;

/**
 * @author yoqu
 * @date 2017年07月11日
 * @time 上午9:21
 * @email wcjiang2@iflytek.com
 */
public class AngelBuilder extends ActorBuilder {
    public void buildType() {
        actor.setType("天使");
    }

    public void buildFace() {
        actor.setFace("圆脸");
    }

    public void buildSex() {
        actor.setSex("女");
    }

    public void buildHairStyle() {
        actor.setHairstyle("短发");
    }
}
