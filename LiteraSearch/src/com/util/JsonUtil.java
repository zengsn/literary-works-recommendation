package com.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ��Java����ת��ΪJson��ʽ���ַ���
 * @author CJP
 * 
 */
public class JsonUtil {

	private static final String noData = "{\"result\":null}";
	// Ĭ��Json�ַ�����nullֵ����������·��ظ�ֵ

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		// �������ֵΪ�գ�ֱ�Ӻ���
	}

	/**
	 * @Description:��Objectת��ΪJson�ַ���
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
	 * @Description:��Json�ַ���ת��ΪJsonNode
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
