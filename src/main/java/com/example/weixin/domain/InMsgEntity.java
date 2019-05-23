package com.example.weixin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class InMsgEntity {

    private String ToUserName;	      //开发者微信号
    private String FromUserName ;	 //发送方帐号（一个OpenID）
    private Long CreateTime;	       //消息创建时间 （整型）
    private String MsgType;        //消息类型，文本为text，图片为image
    private String Content;	     //文本消息内容
    private Long MsgId;	        //消息id，64位整型

    private String PicUrl;	   //图片链接（由系统生成）
    private String MediaId;   //图片消息媒体id，可以调用获取临时素材接口拉取数据。
    private String Format;	 //语音格式，如amr，speex等
    private String MediaID;  //	语音消息媒体id，可以调用获取临时素材接口拉取该媒体

    private String Recognition;	//语音识别结果，UTF8编码

    private String ThumbMediaId; //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。

    private String Location_X;	//地理位置维度
    private String Location_Y;	//地理位置经度
    private String Scale;	//地图缩放大小
    private String Label;	//地理位置信息

    private String Title;	//消息标题
    private String Description;	//消息描述
    private String Url;   //消息链接

    private String Event;//	事件类型，subscribe(订阅)、unsubscribe(取消订阅)、CLICK

    private String EventKey;//事件KEY值，与自定义菜单接口中KEY值对应

}
