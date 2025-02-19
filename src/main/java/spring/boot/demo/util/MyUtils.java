package spring.boot.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2020/9/24 16:41
 */

public class MyUtils {

    /**
     * 获得IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * 获得Md5加密
     *
     * @param str 原字符串
     * @return 加密后的字符串
     */
    public static String strToMd5(String str) {
        String md5Str = null;
        if (str != null && str.length() != 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte b[] = md.digest();

                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0) {
                        i += 256;
                    }
                    if (i < 16) {
                        buf.append("0");
                    }
                    buf.append(Integer.toHexString(i));
                }
                //32位
                md5Str = buf.toString();
                //16位
                //md5Str = buf.toString().substring(8, 24);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return md5Str;
    }

    /**
     * 根据email获取gravatar头像
     * @param email Email
     * @return 头像URL
     */
    public static String getGravatar(String email) {
        String emailMD5 = strToMd5(email);
        //设置图片大小32px
//        String avatar = "http://cn.gravatar.com/avatar/" + emailMd5 + "?s=128&d=identicon&r=PG";
        String avatar = "/gravatar/download/img/?emailMD5=" + emailMD5;
        return avatar;
    }

    /**
     * ECharts图表数据生成
     * @param maps
     * @return
     */
    public static List<List<String>> listMap2ListListECharts(List<Map<String, Object>> maps) {
        List<List<String>> result = new LinkedList<>();
        List<String> x = new LinkedList<>();
        List<String> y = new LinkedList<>();
        maps.forEach(reserveMap -> {
            Set<String> keySet = reserveMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            int i = 0;
            while (iterator.hasNext()){
                String key = iterator.next();
                String value = reserveMap.get(key).toString();
                if (i % 2 == 0){
                    x.add(value);
                } else {
                    y.add(value);
                }
                i++;
            }

        });
        result.add(x);
        result.add(y);
        return result;
    }

}
