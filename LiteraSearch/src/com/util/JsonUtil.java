package com.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 将Java对象转化为Json格式的字符串
 * @author CJP
 * 
 */
public class JsonUtil {

	private static final String noData = "{\"result\":null}";
	// 默认Json字符串，null值或错误的情况下返回该值

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		// 如果属性值为空，直接忽略
	}

	/**
	 * @Description:将Object转化为Json字符串
	 * @param object
	 * @return
	 */
	public static String parseJson(Object object) {
		if (object == null) {
			return noData;
		}
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return noData;
		}
	}

	/**
	 * @Description:将Json字符串转化为JsonNode
	 * @param json
	 * @return
	 */
	public JsonNode json2Object(String json) {
		try {
			return mapper.readTree(json);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
