package com.kdkj.caijin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class CaijinApplicationTests {

//    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
//        String[] strings = StringUtils.commaDelimitedListToStringArray("w,ww,rer,trtr");
//        for (String a:strings){
//            log.info(a);
//        }
        RestTemplate restTemplate = new RestTemplate();
//        File forObject = restTemplate.getForObject("http://127.0.0.1:8088/2018-04-1915540258262546_p0.jpg", File.class);
        ClientHttpRequestFactory requestFactory = restTemplate.getRequestFactory();
        ClientHttpRequest request = requestFactory.createRequest(new URI("http://127.0.0.1:8088/2018-04-1915540258262546_p0.jpg"), HttpMethod.GET);

        ClientHttpResponse execute = request.execute();
        InputStream body = execute.getBody();
        int a=0;
        byte[] b=new byte[1024*4];
        OutputStream outputStream = new FileOutputStream(new File("D:\\qqqqqqqq.jpg"));
        while ((a = body.read(b)) != -1) {
            outputStream.write(b, 0, a);
        }
        if (outputStream!=null){
            outputStream.close();
        }
        if (body!=null){
            body.close();
        }
//        FileCopyUtils.copyToByteArray()
//        FileCopyUtils.copy(b,new File("D:\\qqqqqqqq.jpg"));
    }
}
