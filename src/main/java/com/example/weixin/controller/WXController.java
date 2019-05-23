package com.example.weixin.controller;

import com.example.weixin.domain.ArticlesItem;
import com.example.weixin.domain.InMsgEntity;
import com.example.weixin.domain.OutMsgEntity;
import com.example.weixin.util.SecurityUtil;
import com.example.weixin.util.WXUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;

/**
 * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上
 * 验证消息的确来自微信服务器
 */
@Controller
public class WXController {
    /**
     * 将token、timestamp、nonce三个参数进行字典序排序
     * 将三个参数字符串拼接成一个字符串进行sha1加密
     * 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * @return
     */
    @RequestMapping(value = "/weChat",method = RequestMethod.GET)
    @ResponseBody
    public String validate(String signature,String timestamp,String nonce,String echostr){
        String []arr={WXUtil.TOKEN,timestamp,nonce};
        Arrays.sort(arr);
        StringBuffer sb=new StringBuffer();
        for (String temp :arr){
            sb.append(temp);
        }
        String mySignature = SecurityUtil.SHA1(sb.toString());
        if(mySignature.equals(signature)){
            System.out.println("验证消息的确来自微信服务器成功!");
            return echostr;
        }
        System.out.println("验证消息的确来自微信服务器失败!");
        return null;
    }

    /**
     * 消息处理
     * @param inMsgEntity
     * @return
     */
    @RequestMapping(value = "/weChat",method = RequestMethod.POST,produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    public Object handelMessage(@RequestBody InMsgEntity inMsgEntity){
        System.out.println("接收到的消息内容"+inMsgEntity.getContent());
        OutMsgEntity outMsgEntity=new OutMsgEntity();
        outMsgEntity.setFromUserName(inMsgEntity.getToUserName());
        outMsgEntity.setToUserName(inMsgEntity.getFromUserName());
        outMsgEntity.setCreateTime(new Date().getTime());
        String msgType = inMsgEntity.getMsgType();
        String content=inMsgEntity.getContent();
        String outContent=null;
        if(msgType.equals("text")){
            outMsgEntity.setMsgType("text");
            //关键字回复
            if(content.contains("生活")){
                outContent="生活是一场修行";
            }else if(content.contains("健康")){
                outContent="熬了下半夜\n输了后半生";
            }else{
                outContent=content;
            }
            outMsgEntity.setContent(outContent);
        }else if(msgType.equals("image")){
            outMsgEntity.setMsgType("image");
            outMsgEntity.setMediaId(new String[]{(inMsgEntity.getMediaId())});
        }else if(msgType.equals("event")){
            //事件类型，subscribe(订阅)、unsubscribe(取消订阅)
            if(inMsgEntity.getEvent().equals("subscribe")){
              //回复图文消息
                outMsgEntity.setMsgType("news");
                outMsgEntity.setArticleCount(1);
                ArticlesItem item = new ArticlesItem();
                item.setTitle("中华有为真英雄");
                item.setPicUrl("https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/s%3D220/sign=190d8222516034a82de2bf83fb1349d9/b812c8fcc3cec3fd7c2bac56db88d43f879427ff.jpg");
                item.setDescription("任正非43岁才开始创业，不惑之年始见春，一手把山寨公司变成了震惊世界的科技王国");
                item.setUrl("https://baike.baidu.com/item/%E4%BB%BB%E6%AD%A3%E9%9D%9E/448501?fr=aladdin");
                outMsgEntity.setItem(new ArticlesItem[]{item});
            }else if(inMsgEntity.getEvent().equals("CLICK")){
                //点击菜单
                String eventKey = inMsgEntity.getEventKey();
                if("companyIntroduction".equals(eventKey)){
                    outContent="华为是全球领先的ICT（信息与通信）基础设施和智能终端提供商，致力于把数字世界带入每个人、每个家庭、每个组织，构建万物互联的智能世界。我们在通信网络、IT、智能终端和云服务等领域为客户提供有竞争力、安全可信赖的产品、解决方案与服务，与生态伙伴开放合作，持续为客户创造价值，释放个人潜能，丰富家庭生活，激发组织创新。华为坚持围绕客户需求持续创新，加大基础研究投入，厚积薄发，推动世界进步。华为成立于1987年，是一家由员工持有全部股份的民营企业，目前有18万员工，业务遍及170多个国家和地区。";
                  }else if("companyNews".equals(eventKey)){
                    outContent="广东移动加快推进5G商用，使能千行百业与华为签署5G园区战略合作协议，启动首个5G友好用户放号" ;
                }
                outMsgEntity.setContent(outContent);
                outMsgEntity.setMsgType("text");
            }
        }
        return outMsgEntity;
    }

}
