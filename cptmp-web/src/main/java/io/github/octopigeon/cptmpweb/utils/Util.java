package io.github.octopigeon.cptmpweb.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author anlow
 * @version 1.0
 * @date 2020/7/8
 * @last-check-in anlow
 * @date 2020/7/8
 */
public class Util {

    public static String getHttpRequestBody(HttpServletRequest httpServletRequest) throws IOException {
        StringBuilder json = new StringBuilder();
        String str = "";
        while ((str = httpServletRequest.getReader().readLine()) != null) {
            json.append(str);
        }
        return json.toString();
    }

}
