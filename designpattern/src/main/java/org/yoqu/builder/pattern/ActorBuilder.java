package org.yoqu.builder.pattern;

/**
 * @author yoqu
 * @date 2017年07月11日
 * @time 上午9:19
 * @email wcjiang2@iflytek.com
 */
public  abstract  class ActorBuilder {
    protected Actor actor = new Actor();
    public abstract void buildType();
    public abstract void buildFace();
    public abstract void buildSex();
    public abstract void buildHairStyle();
    public Actor createActor(){
        return actor;
    }
}
