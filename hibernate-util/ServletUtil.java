package com.joiniot.lock.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ServletUtil {
	
	public static List<SearchParam> buildWhere(HttpServletRequest request){
		List<SearchParam> result = new ArrayList<SearchParam>();
		Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            //? q_C_like_name=java

            String name = names.nextElement(); //请求名称 q_like_xxx_xxx
            String value = request.getParameter(name); //请求的值 java
            try {
                value = new String(value.getBytes("ISO8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (name.startsWith("q_") && StringUtils.isNotEmpty(value)) {
                String[] array = name.split("_", 4);
                if (array.length < 4) {
                    throw new IllegalArgumentException("查询参数构建错误:" + name);
                } else {
                    String propertyName = array[3];
                    String type = array[2];
                    String dataType = array[1];

                    Object propertyValue = null;
                    if("s".equalsIgnoreCase(dataType)) {
                        propertyValue = value;
                    } else if("i".equalsIgnoreCase(dataType)) {
                        propertyValue = Integer.valueOf(value);
                    } else if("d".equalsIgnoreCase(dataType)) {
                        propertyValue = Double.valueOf(value);
                    } else if("l".equalsIgnoreCase(dataType)) {
                        propertyValue = Long.valueOf(value);
                    } //....


                    SearchParam searchParam = new SearchParam(type,propertyName,propertyValue);

                    result.add(searchParam);
                    request.setAttribute(name,value);
                }
            }


        }
		return result;
	}
}
