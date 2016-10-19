package com.luo.common;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 工具类
 * @author SLL
 *
 */
public class BaseUtil {
    /**
     * 
     * 驼峰转大写/下划线
     * @param param
     * @return
     */
    public static String toUC(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_"
                    + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString().toUpperCase();
    }
    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            //return name.substring(0, 1).toLowerCase() + name.substring(1);
            return name.toLowerCase();
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    /**
     * 驼峰格式转大写
     * @param o1
     * @return
     */
    public static JSONObject low2Up(JSONObject o1){
        JSONObject o2=new JSONObject();
         Iterator it = o1.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object object = o1.get(key);
//                System.out.println(object.getClass().toString());
                if(object.getClass().toString().endsWith("String")){
                    o2.accumulate(BaseUtil.toUC(key), object);
                }else if(object.getClass().toString().endsWith("JSONObject")){
                    o2.accumulate(BaseUtil.toUC(key), BaseUtil.low2Up((JSONObject)object));
                }else if(object.getClass().toString().endsWith("JSONArray")){
                    o2.accumulate(BaseUtil.toUC(key), BaseUtil.low2Array(o1.getJSONArray(key)));
                }else{
                    o2.accumulate(BaseUtil.toUC(key), object);
                }
            }
            return o2;
    }
    
    public static JSONArray low2Array(JSONArray o1){
        JSONArray o2 = new JSONArray();
        for (int i = 0; i < o1.size(); i++) {
            Object jArray=o1.getJSONObject(i);
            if(jArray.getClass().toString().endsWith("JSONObject")){
                o2.add(BaseUtil.low2Up((JSONObject)jArray));
            }else if(jArray.getClass().toString().endsWith("JSONArray")){
                o2.add(BaseUtil.low2Array((JSONArray)jArray));
            }
        }
        return o2;
    }
    
    /**
     * 大写 转驼峰格式
     * @param o1
     * @return
     */
    public static JSONObject up2Low(JSONObject o1){
        JSONObject o2=new JSONObject();
         Iterator it = o1.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object object = o1.get(key);

//              System.out.println(object.getClass().toString()+"======"+object.toString());
                if(object.getClass().toString().endsWith("String")){
                    o2.accumulate(BaseUtil.camelName(key), object);
                }else if(object.getClass().toString().endsWith("JSONObject")){
                    o2.accumulate(BaseUtil.camelName(key), BaseUtil.up2Low((JSONObject)object));
                }else if(object.getClass().toString().endsWith("JSONArray")){
                    o2.accumulate(BaseUtil.camelName(key), BaseUtil.upArray(o1.getJSONArray(key)));
                }else{
                    o2.accumulate(BaseUtil.camelName(key), object);
                }
            }
            return o2;
    }
    public static JSONArray upArray(JSONArray o1){
        JSONArray o2 = new JSONArray();
        for (int i = 0; i < o1.size(); i++) {
                Object object = o1.get(i);
                if(object.getClass().toString().endsWith("String") 
                    || object.getClass().toString().endsWith("Integer") 
                    || object.getClass().toString().endsWith("Double")
                    || object.getClass().toString().endsWith("BigDecimal")){
                    o2.add(object);
                }else{
                    Object jArray=o1.getJSONObject(i);
                    if(jArray.getClass().toString().endsWith("JSONObject")){
                        o2.add(BaseUtil.up2Low((JSONObject)jArray));
                    }else if(jArray.getClass().toString().endsWith("JSONArray")){
                        o2.add(BaseUtil.upArray((JSONArray)jArray));
                    }
                }
        }
        return o2;
    }
    
    
    /**
     * 实体转Map
     * @param req
     * @return
     */
    public static Map<String, Object> beanToMap(Serializable req) { 
        Map<String, Object> Map = new HashMap<String, Object>(); 
        try { 
          PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
          PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(req); 
          for (int i = 0; i < descriptors.length; i++) { 
            String names = descriptors[i].getName(); 
           // String name=BaseUtil.toUC(names).toUpperCase();
            if (!StringUtils.equals(names, "class")) { 
                Map.put(names, propertyUtilsBean.getNestedProperty(req, names)); 
            } 
          } 
        } catch (Exception e) { 
          e.printStackTrace(); 
        } 
        return Map; 
    
    }

    /**
     * 将map数据合并JSON
     * 
     * @author SLL
     *
     */
    public static JSONObject data(JsonNode json) {
    	JSONObject jso = new JSONObject();
    	try{
    		 	JSONObject jsonObject = data2(json);

    	        JSONObject jsonResponse = jsonObject.getJSONObject("service").getJSONObject("serviceBody").getJSONObject("response");
    	        JSONObject jsonServiceHeader = jsonObject.getJSONObject("service").getJSONObject("serviceHeader");

    	        
    	        jso.putAll(jsonResponse);
    	        jso.putAll(jsonServiceHeader);
    	        return jso;
    	}catch(Exception e){
    		 
    	}
       

        return null;
     
    }

    /**
     * 将map数据合并JSON
     *
     * @author SLL
     *
     */
    public static JSONObject data2(JsonNode json) {
        JSONObject jbj = JSONObject.fromObject(json.toString());
        return BaseUtil.up2Low(jbj);
    }
    /**
     * 将字符串传 Base64
     * @param plainText
     * @return
     */
    public static String encodeStr(String plainText){
        byte[] b=plainText.getBytes();
        Base64 base64=new Base64();
        b=base64.encode(b);
        String s=new String(b);
        return s;
    } 
}
