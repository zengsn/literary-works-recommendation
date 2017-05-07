package com.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Page;
import com.service.WebService;
import com.util.StringUtils;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = -8650607314737411085L;

	private WebService service = new WebService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String keywords = request.getParameter("searchKeywords");
		String currPageNO = request.getParameter("currPageNO");
		Page page = null;
		if (StringUtils.isNotEmpty(keywords)
				&& StringUtils.isNotEmpty(currPageNO)) {
			page = service.show(keywords, Integer.parseInt(currPageNO));
			request.setAttribute("page", page);
			request.setAttribute("searchKeywords", keywords);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("page", page);
			paramMap.put("searchKeywords", keywords);
			request.setAttribute("paramMap", paramMap);

		}
		request.getRequestDispatcher("/generic/search1.jsp").forward(request,
				response);
	}

}
