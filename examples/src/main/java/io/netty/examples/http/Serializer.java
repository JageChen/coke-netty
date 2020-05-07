package io.netty.examples.http;

/**
 * description: Serializer <br>
 * date: 2020/5/7 17:26 <br>
 * author: EDZ <br>
 * version: 1.0 <br>
 */
public interface Serializer {
    /**
     *todo  java 对象转换成二进制
     */
    byte[] serialize(Object object);
    /**
     *todo  二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
