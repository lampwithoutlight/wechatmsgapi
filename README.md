# wechatmsgapi
微信发送模板消息或发消息给公众号用户的接口。

two apis

1.static void sendTemplateMsgToOpenID(String appid,String scret,String templateID,String openid, JSONObject dataobj)
This api use to send template messages to on openid. 

2.static void sendMsgToOpenIDs(String appid,String scret,List<String> openids,String msg)
This api use to send  messages to  one or more openids.
