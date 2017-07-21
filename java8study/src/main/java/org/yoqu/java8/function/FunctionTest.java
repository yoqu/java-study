package org.yoqu.java8.function;

import org.yoqu.java8.App;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author yoqu
 * @date 2017年07月14日
 * @time 上午11:21
 * @email wcjiang2@iflytek.com
 */
public class FunctionTest {
    public static void main(String[] args) {
        Function<Integer, String> func1 = (x) -> "test" + x;
        Function<String, String> func2 = (x) -> {
            return "After test" + x;
        };
        System.out.println(func1.apply(666));
        System.out.println(func1.andThen(func2).apply(6));
        FunctionTest t = new FunctionTest();
        t.testMapper();
        List<Type> types = new ArrayList<>();
        types.add(EntitySupplier.class.getGenericSuperclass());
        types.addAll(Arrays.asList(EntitySupplier.class.getGenericInterfaces()));
        types.stream()
                .map(type -> {
                    return Object.class;
                }).findFirst().orElse(Object.class);

    }

    public void testMapper() {
        MapperFactory factory = new MapperFactory();
        App app = factory.instance(App.class, "app");
        app.setAppName("Hello");
        System.out.println(app);
        App app1 = factory.instance(App.class, "app");
        app1.setAppName("World");
        System.out.println(app1);
    }

    protected class MapperFactory {
        private Map<String, Mapper> mappers;
        public <T> T instance(Class<T> tClass, String name) {
            if (mappers == null) {
                mappers = new HashMap<>();
            }
            if (mappers.containsKey(name)) {
                return (T) mappers.get(name).supplier.get();
            } else {
                Mapper<T> mapper = initCache(tClass);
                mappers.put(name, mapper);
                return mapper.supplier.get();
            }
        }

        public <T> Mapper<T> initCache(Class<T> beanClass) {
            return new Mapper<>(new EntitySupplier<T>(beanClass), beanClass);
        }
    }

    protected class Mapper<T> {
        private Supplier<T> supplier;
        private Class classz;

        public Mapper(Supplier s, Class classz) {
            this.supplier = s;
            this.classz = classz;
        }
    }

    protected class EntitySupplier<T> extends App implements Supplier<T> {
        private Class<T> tClass;

        public EntitySupplier(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public T get() {
            try {
                return tClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
