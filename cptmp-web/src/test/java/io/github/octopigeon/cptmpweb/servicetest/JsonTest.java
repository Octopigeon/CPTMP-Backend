package io.github.octopigeon.cptmpweb.servicetest;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/18
 * @last-check-in Gh Li
 * @date 2020/7/18
 */
@Data
class A{
    private String name;
    private Integer num;

    public A(String s, int i) {
        setName(s);
        setNum(i);
    }
}

public class JsonTest {
    @Test
    public void test(){
        List<A> a = new ArrayList<>() ;
//        a.add(new A("//////1", 1)) ;
//        a.add(new A("/////2", 2)) ;
//        a.add(new A("\\\3", 3)) ;
        Object o = JSONObject.toJSON(a);
        System.out.println(o.toString()) ;
    }
}
