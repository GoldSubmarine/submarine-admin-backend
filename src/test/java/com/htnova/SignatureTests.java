package com.htnova;

import com.htnova.signature.utils.SignatureClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SignatureTests {

    @Test
    void getTest() {
        String url =
                "http://localhost:19687/test/1?param="
                        + RandomStringUtils.randomAlphanumeric(10)
                        + "&d=123&t="
                        + System.currentTimeMillis();
        String sign = SignatureClient.getInstance("112233").sign("GET", url);
        System.out.println("sign:" + sign);
        url += "&s=" + sign;
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url, String.class);
        System.out.println(forEntity.getBody());
    }

    @Test
    void postTest() {
        String url = "http://localhost:19687/test/2?d=123&t=" + System.currentTimeMillis();
        String body = RandomStringUtils.randomAlphanumeric(1000);
        String sign = SignatureClient.getInstance("112233").sign("POST", url, body);
        url += "&s=" + sign;
        System.out.println("sign:" + sign);
        ResponseEntity<String> forEntity =
                new RestTemplate().postForEntity(url, body, String.class);
        System.out.println(forEntity.getBody());
    }
}
