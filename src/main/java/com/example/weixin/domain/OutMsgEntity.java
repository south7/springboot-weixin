package com.example.weixin.domain;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity {

    private String ToUserName;//	是	接收方帐号（收到的OpenID）
    private String FromUserName;//	是	开发者微信号
    private Long CreateTime;//	是	消息创建时间 （整型）
    private String MsgType;//	是	消息类型，文本为text
    private String Content;//	是	回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
    @XmlElementWrapper(name="Image")
    private String[] MediaId;//	是	通过素材管理中的接口上传多媒体文件，得到的id。

    private String Title;//	否	视频消息的标题
    private String Description;//	否	视频消息的描述

    private String MusicURL;//	否	音乐链接
    private String HQMusicUrl;//	否	高质量音乐链接，WIFI环境优先使用该链接播放音乐
    private String ThumbMediaId;//	是	缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id

    private Integer ArticleCount;//	是	图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息


    @XmlElementWrapper(name="Articles")
    private ArticlesItem[] item;

}
