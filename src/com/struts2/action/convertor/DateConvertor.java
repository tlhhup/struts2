package com.struts2.action.convertor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateConvertor extends DefaultTypeConverter {

	@Override
	public Object convertValue(Map<String, Object> context, Object value, Class toType) {
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			if(toType==Date.class){
				String[] params = (String[]) value;// 注意该value实质为数组类型的数据
				return format.parse(params[0]);
			}else if(toType==String.class){
				return format.format(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
