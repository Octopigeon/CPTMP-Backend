package io.github.octopigeon.cptmpservice.utils;

import okhttp3.OkHttpClient;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/13
 * 提供service层及以上的所有常用方法
 * @last-check-in anlow
 * @date 2020/7/13
 */
public class Utils {

    /**
     * Returns an array of null properties of an object
     * @param source BeanUtils复制属性时的源
     * @return 返回值为空的所有属性名
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static OkHttpClient getOkhttpClientWithProxy() {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS,
                new InetSocketAddress("localhost", 10808));
        return new OkHttpClient().newBuilder().proxy(proxy)
                .build();
    }

}
