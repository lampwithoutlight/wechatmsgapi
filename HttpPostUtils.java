package wxmsgServer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

 


 
 

 








import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
 




import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
 

 

public class HttpPostUtils {
   
     
	public static String get(String url) {  
        
        HttpClient client = new   DefaultHttpClient();  
        HttpGet get = new HttpGet(url);  
        JSONObject json = null;  
        try {  
            HttpResponse res = client.execute(get);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(entity.getContent(),"UTF-8"), 8 * 1024);
                    StringBuilder entityStringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        entityStringBuilder.append(line + "/n");
                    }
                    
                    return entityStringBuilder.toString();
                    // 利用从HttpEntity中得到的String生成JsonObject
                  
                } catch (Exception e) {
                    e.printStackTrace();
                }
                json = new JSONObject();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
              
        } finally{  
            //关闭连接 ,释放资源  
            client.getConnectionManager().shutdown();  
        }  
        return "";  
    }  
    
    
    public static String saveImageToDisk(String imageurl,String path) throws IOException {
    	
    	new java.io.File( path ).mkdir();
		String filename= UUID.randomUUID().toString()+".jpg";
		  
            File file = new File(path, filename);  
         
    	
    	
        InputStream inputStream = getInputStream(imageurl);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
		return filename;
 
    }
 
    /**
     * ��÷����������ݣ���InputStream��ʽ����
     *
     * @return
     * @throws IOException
     */
    public static InputStream getInputStream(String imgUrl) throws IOException {
        InputStream inputStream = null;
        HttpsURLConnection httpURLConnection = null;
        try {
            URL url = new URL(imgUrl);
            if (url != null) {
                httpURLConnection = (HttpsURLConnection) url.openConnection();
                // ������������ĳ�ʱʱ��
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                // ���ñ���http����ʹ��get��ʽ����
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // �ӷ��������һ��������
                    inputStream = httpURLConnection.getInputStream();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        return inputStream;
    }
    
    public static String httpsPostJson(String urlAddress, JSONObject obj){
    	
    	try {
            //��������
            URL url = new URL(urlAddress);
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            String para=obj.toString();
            System.out.println("this is "+para); 
           // para=URLEncoder.encode(para,"UTF-8");
          // String jsonstr = URLEncoder.encode(obj.toString(), "utf-8");  
          //  connection.set
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-javascript; charset=utf-8");
         //   connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
          //  connection.setRequestProperty("Content-type", "text/html");  
          //  connection.setRequestProperty("Accept-Charset", "utf-8");  
          //  connection.setRequestProperty("contentType", "utf-8");  

            
           
           // post.getParams().setContentCharset("utf-8");
            
            connection.connect();

            //POST����
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            
 
         out.write(obj.toString().getBytes("utf-8"));
            out.flush();
            out.close();
            
            
            
            
            System.out.println(para); 
            //��ȡ��Ӧ  
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
           
            // �Ͽ�����
            connection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	return "";
    }
 public static String httpPostJson(String urlAddress, JSONObject obj){
    	
    	try {
            //��������
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            String para=obj.toString();
            System.out.println("this is "+para); 
           // para=URLEncoder.encode(para,"UTF-8");
          // String jsonstr = URLEncoder.encode(obj.toString(), "utf-8");  
          //  connection.set
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-javascript; charset=utf-8");
         //   connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
          //  connection.setRequestProperty("Content-type", "text/html");  
          //  connection.setRequestProperty("Accept-Charset", "utf-8");  
          //  connection.setRequestProperty("contentType", "utf-8");  

            
           
           // post.getParams().setContentCharset("utf-8");
            
            connection.connect();

            //POST����
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            
 
         out.write(obj.toString().getBytes("utf-8"));
            out.flush();
            out.close();
            
            
            
            
            System.out.println(para); 
            //��ȡ��Ӧ  
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
           
            // �Ͽ�����
            connection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	return "";
    }
    
    public static String httpsGet(String urlAddress,String []params){
        URL url = null;
        HttpsURLConnection con  =null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            url = new URL(urlAddress);
            con  = (HttpsURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            String paramsTemp = "";
            for(String param:params){
                if(param!=null&&!"".equals(param.trim())){
                    paramsTemp+="&"+param;
                }
            }
            
            System.out.println(url+paramsTemp);
            byte[] b = paramsTemp.getBytes();
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (true) {
              String line = in.readLine();
              if (line == null) {
                break;
              }
              else {
                  result.append(line);
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(in!=null){
                    in.close();
                }
                if(con!=null){
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
    public static String httpGet(String urlAddress,String []params){
        URL url = null;
        HttpURLConnection con  =null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            url = new URL(urlAddress);
            con  = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            String paramsTemp = "";
            for(String param:params){
                if(param!=null&&!"".equals(param.trim())){
                	
                    paramsTemp+="&"+param;
                }
            }
            
            System.out.println(url+paramsTemp);
            byte[] b = paramsTemp.getBytes();
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (true) {
              String line = in.readLine();
              if (line == null) {
                break;
              }
              else {
            	  line = new String(line.getBytes(), "utf-8");  
                  result.append(line);
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(in!=null){
                    in.close();
                }
                if(con!=null){
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
    
    public static String httpsPost(String urlAddress,String []params){
        URL url = null;
        HttpsURLConnection con  =null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            url = new URL(urlAddress);
            con  = (HttpsURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            String paramsTemp = "";
            for(String param:params){
                if(param!=null&&!"".equals(param.trim())){
                    paramsTemp+="&"+param;
                }
            }
            
            System.out.println(url+paramsTemp);
            byte[] b = paramsTemp.getBytes();
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (true) {
              String line = in.readLine();
              if (line == null) {
                break;
              }
              else {
                  result.append(line);
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(in!=null){
                    in.close();
                }
                if(con!=null){
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
    
   
} 