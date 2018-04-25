package com.kdkj.caijin.thirdpartylogin;

import com.alibaba.fastjson.JSON;
import com.kdkj.caijin.util.ErrMsgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ResourceBundle;

/**
 * @author lin
 * @create 2018-04-20 11:15
 **/
@Component
@Slf4j
public class Wxthirdpartylogin {
    @Autowired
    private RestTemplate restTemplate;

    public WxOauth2 getWxAccessToken(String code){
        // 读取wx配置文件的信息
        ResourceBundle resource = ResourceBundle.getBundle("wx");
        StringBuffer sb = new StringBuffer();
        sb.append("appid=").append(resource.getString("appid"));
        sb.append("&secret=").append(resource.getString("secret"));
        sb.append("&code=").append(code);
        sb.append("&grant_type=").append(resource.getString("grant_type"));
        //
        String url=resource.getString("wx_auth_login_url") + "?" + sb.toString();
        log.info(url);
        String res = restTemplate.getForObject(url,String.class);
        log.info(res.toString());
//        String res = HttpRequestTools.sendGet(resource.getString("get_session_key_url"), sb.toString());
//        if (res == null || res.equals("")) {
//            return null;
//        }
        return JSON.parseObject(res, WxOauth2.class);
    }

    public WxUserInfo getUserInfo(WxOauth2 wxOauth2){
        // 读取wx配置文件的信息
        ResourceBundle resource = ResourceBundle.getBundle("wx");
        String openid = wxOauth2.getOpenid();
        if (StringUtils.isEmpty(openid)){
            throw new ErrMsgException("openid不能为空");
        }
        String access_token = wxOauth2.getAccess_token();
        if (StringUtils.isEmpty(access_token)){
            throw new ErrMsgException("access_token不能为空");
        }
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("access_token=").append(access_token);
        stringBuffer.append("&openid=").append(access_token);
        String url=resource.getString("wx_userinfo_url")+"?"+stringBuffer.toString();
        String res = restTemplate.getForObject(url, String.class);
        return JSON.parseObject(res, WxUserInfo.class);
    }
}
