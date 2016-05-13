package wxmsgServer;

 
import java.util.List;
 

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
 
import net.sf.json.JSONObject;

 

public class weixinmsgapi {
	 public static Logger logger =  Logger.getLogger(Log.class);  
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
  
	 
	}
	 
	 
	public static String getAccessToken(String appid, String secret) {
		String para[] = new String[3];
		para[0] = "grant_type=client_credential";
		para[1] = "appid=" + appid;
		para[2] = "secret=" + secret;

		String posturl = "https://api.weixin.qq.com/cgi-bin/token";

		logger.info("post=" + posturl + ":" + para.toString());
		String reJson = HttpPostUtils.httpsGet(posturl, para);
		JSONObject reObj = JSONObject.fromObject(reJson);
		String token = (String) reObj.get("access_token");
		return token;
	}
	 
	
	static void sendTemplateMsgToOpenID(String appid,String scret,String templateID,String openid, JSONObject dataobj)
	{
		 
		
		 
	 
		 String token = getAccessToken(appid,scret);
		      String posturl ="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+java.net.URLEncoder.encode(token);
		      
		      JSONObject obj = new JSONObject();
		      obj.element("touser", openid);
		      
		      obj.element("template_id", templateID);
		      obj.element("url", "http://m.fandebmw.com/member/vip_club_or_banding_vip_card/");
		      obj.element("topcolor", "#4169E1");
		      obj.element("data", dataobj);
		      
		      logger.info("post=" + posturl);
		      String reJson = HttpPostUtils.httpsPostJson(posturl, obj);
		      logger.info(reJson);
	}
	
	 
	static void sendMsgToOpenIDs(String appid,String scret,List<String> openids,String msg)
	{
		String openidstr="";
		for(int i=0;i<openids.size();i++)
		{
			String openid=openids.get(i);
			openidstr=openidstr+"\""+openid+"\",";
		}
		if(openidstr.length()>0)
			openidstr=openidstr.substring(0,openidstr.length()-1);
		
	  
		 String token = getAccessToken(appid,scret);
		      String posturl ="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+java.net.URLEncoder.encode(token);
		      
		      JSONObject obj = new JSONObject();
		      obj.element("touser", "["+openidstr+"]");
		      
		      obj.element("msgtype", "text");
		      
		      JSONObject txtobj = new JSONObject();
		      txtobj.element("content", msg);
		      obj.element("text", txtobj);
		      logger.info("post=" + posturl);
		      String reJson = HttpPostUtils.httpsPostJson(posturl, obj);
		      logger.info(reJson);
	}
}
