package org.yoqu.builder.pattern;

/**
 * @author yoqu
 * @date 2017年07月11日
 * @time 上午9:22
 * @email wcjiang2@iflytek.com
 */
public class BuilderMain {
    public static void main(String[] args) {
        BuilderMain main = new BuilderMain();
        main.buildActor();
    }

    public void buildActor() {
        ActorBuilder actorBuilder = getActorBuilder();
        actorBuilder.buildFace();
        actorBuilder.buildHairStyle();
        actorBuilder.buildSex();
        actorBuilder.buildType();
        Actor actor = actorBuilder.createActor();
        System.out.println(actor);
    }

    public ActorBuilder getActorBuilder() {
        return new AngelBuilder();
    }
}
