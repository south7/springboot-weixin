package com.example.weixin.util;


import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Date;

public class WXUtil {
    public  static final String TOKEN="weixinwangfan";
    private static final String APPID="wxb30be77cd7325e32";
    private static final String APPSECRET="35470f5c12d8445340f4dca06a53c7f4";
    private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String DELETE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    private static final String SEND_TEMPLATE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    private static final String GET_ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static  String access_token;
    private static  Long access_token_expiresTime;//access_token到期时间

    /**
     * 获取access_token
     * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
     * @return access_token
     */
    public static String getAccess_token(){
        if(StringUtils.isEmpty(access_token)||new Date().getTime()>access_token_expiresTime){
            //正常情况下，微信会返回下述JSON数据包给公众号：//{"access_token":"ACCESS_TOKEN","expires_in":7200}
            String result = HttpUtil.get(GET_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET));
            JSONObject jsonObject = JSONObject.parseObject(result);
            access_token = jsonObject.getString("access_token");
            Long expires_in = jsonObject.getLong("expires_in");
            access_token_expiresTime=new Date().getTime()+(expires_in*1000);
        }
        return access_token;
    }

    /**
     * 创建菜单
     * @param menuJson
     */
    public static void createMenu(String menuJson){
        //正确时的返回JSON数据包如下：{"errcode":0,"errmsg":"ok"}
        String result = HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN", getAccess_token()), menuJson);
        System.out.println(result);
    }

    /**
     * 删除菜单
     * 正确的Json返回结果:{"errcode":0,"errmsg":"ok"}
     */
    public static void deleteMenu(){
        String result = HttpUtil.get(DELETE_MENU_URL.replace("ACCESS_TOKEN", getAccess_token()));
        System.out.println(result);
    }

    /**
     * 发送模板消息
     * @param data
     */
    public static void sendTemplate(String data){
        String result=HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", getAccess_token()),data);
        System.out.println(result);
    }
    public static void main(String[]args){
        String menuJson=" {\n" +
                "     \"button\":[\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"公司简介\",\n" +
                "          \"key\":\"companyIntroduction\"\n" +
                "      },\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"公司新闻\",\n" +
                "          \"key\":\"companyNews\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"相关网站\",\n" +
                "           \"sub_button\":[\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"华为商城\",\n" +
                "               \"url\":\"https://www.vmall.com/\"\n" +
                "            },\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"华为心声社区\",\n" +
                "               \"url\":\"http://xinsheng.huawei.com/cn/index/guest.html\"\n" +
                "            },\n" +
                "       }]\n" +
                " }";
        // createMenu(menuJson);
        //deleteMenu();
        sendTemplate("{\n" +
                "           \"touser\":\"oKUBp6F1CgdmVBGFjolxNgqinIWY\",\n" +
                "           \"template_id\":\"0hQlOAn6HXpt3pKmJXPOzGDKqEmYh9Lgo2LSDl6ORIM\",\n" +
                "           \"url\":\"https://sale.vmall.com/pseries.html\",           \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"华为P30\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"3988元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"2019年5月20日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }");

    }
}
