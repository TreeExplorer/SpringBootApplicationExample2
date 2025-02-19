package spring.boot.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import spring.boot.demo.domain.SysNotice;

import java.util.Scanner;

/**
 * @author Tree
 * @version V1.0
 * @Title:
 * @Description:
 * @date 2022-08-23 16:00
 */
@Slf4j
public class HttpUtil extends Thread{

    @Autowired
    private RestTemplate restTemplate;

    // private static final String URL = "http://58.56.67.126:5000/hpt/v1/ConsumTransactions";
    private static final String URL = "http://47.105.140.127:80/hpt/v1/ConsumTransactions";

    private static final String USER_NAME = "test";

    private static final String PASS_WORD = "test123";

    private Long cardNo;

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (true){
//            cardNo = sc.nextLong();
//            if (cardNo == -10086){
//                System.exit(0);
//            }
            for (int i = 0; i < arr.length; i++) {
                HttpUtil httpUtil = new HttpUtil();
                httpUtil.setCardNo(Long.parseLong(arr[i]));
                httpUtil.start();
            }
//        }

    }

    @Override
    public void run() {
        // 1、构建body参数
        JSONObject jsonObject = new JSONObject();
        String hex = Long.toString(cardNo, 16).toUpperCase();
        jsonObject.put("CardNo", hex);
        jsonObject.put("Mode", 0);
        jsonObject.put("Amount", 1);

        // 2、添加请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("client-id", "3");

        // 3、组装请求头和参数
        HttpEntity<String> formEntity = new HttpEntity<>(JSON.toJSONString(jsonObject), headers);

        // 4、发起post请求
        ResponseEntity<String> stringResponseEntity = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            stringResponseEntity = restTemplate.postForEntity(URL, formEntity, String.class);
            // log.info("ResponseEntity----" + stringResponseEntity);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        // 5、获取http状态码
        int statusCodeValue = stringResponseEntity.getStatusCodeValue();
        log.info("httpCode-----" + statusCodeValue);

        // 6、获取返回体
        String body = stringResponseEntity.getBody();
        log.info("body-----" + body + "\n\n\n");
    }

    public String getData(){
        // 1、构建body参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName", USER_NAME);
        jsonObject.put("Password", PASS_WORD);

        // 2、添加请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // 3、组装请求头和参数
        HttpEntity<String> formEntity = new HttpEntity<>(JSON.toJSONString(jsonObject), headers);

        // 4、发起post请求
        ResponseEntity<String> stringResponseEntity = null;
        try {
            stringResponseEntity = restTemplate.postForEntity(URL, formEntity, String.class);
            log.info("ResponseEntity----" + stringResponseEntity);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        // 5、获取http状态码
        int statusCodeValue = stringResponseEntity.getStatusCodeValue();
        log.info("httpCode-----" + statusCodeValue);

        // 6、获取返回体
        String body = stringResponseEntity.getBody();
        log.info("body-----" + body);

        // 7、映射实体类
        SysNotice sysNotice = JSONObject.parseObject(body, SysNotice.class);
        assert sysNotice != null;
        String data = sysNotice.toString();
        log.info("data-----" + data);

        return data;
    }

    static String[] arr = new String[]{
            "3085736250",
            "0000000025",
            "1078573693",
            "2817505594",
            "3168784560",
            "2945044526",
            "0889244029",
            "768799855555",
            "18369620935",
            "1092600556",
            "0000100103",
            "0000027837",
            "0000008752",
            "0022247328",
            "0000232222",
            "0000005555",
            "9705136643",
            "0000100023",
            "0001100103",
            "0001100023",
            "0000000103",
            "100023",
            "1084990540",
            "1077552700",
            "1084047772",
            "1090782972",
            "1081687676",
            "123456787",
            "0718492014",
            "1274921427",
            "3085736250",
            "3931397998",
            "96854136287",
            "8432059782",
            "0776544678",
            "123456787",
            "8432059782"
    };

    public Long getCardNo() {
        return cardNo;
    }

    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }
}

