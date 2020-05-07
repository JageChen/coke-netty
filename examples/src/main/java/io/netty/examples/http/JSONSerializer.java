package io.netty.examples.http;

import com.alibaba.fastjson.JSON;

/**
 * description: JSONSerializer <br>
 * date: 2020/5/7 17:27 <br>
 * author: EDZ <br>
 * version: 1.0 <br>
 */
public class JSONSerializer implements Serializer  {
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
