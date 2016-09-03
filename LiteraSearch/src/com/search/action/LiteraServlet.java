package com.search.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.lucene.index.model.Page;
import com.search.service.LiteraService;

@SuppressWarnings("serial")
public class LiteraServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");
		if (keyword == null || keyword.trim().length() == 0) {
			keyword = "哈哈";
		}
		String currPageNO = request.getParameter("page");
		if (currPageNO == null || currPageNO.trim().length() == 0) {
			currPageNO = "1";
		}
		LiteraService service = new LiteraService();
		try {
			Page page = service.show(keyword, Integer.parseInt(currPageNO));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", page.getTotalRecords());
			map.put("rows", page.getParaList());
			JSONArray jsonArray = JSONArray.fromObject(map);
			String jsonJava = jsonArray.toString();
			jsonJava = jsonJava.substring(1, jsonJava.length() - 1);
			response.setContentType("text/html;charset=UTF-8");
			// 以流的方式将json数据输出到DataGrid组件
			PrintWriter pw = response.getWriter();
			pw.write(jsonJava);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
