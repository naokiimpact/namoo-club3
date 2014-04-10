package com.namoo.club.web.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/logout.do")
public class LogoutController extends HttpServlet {

	private static final long serialVersionUID = -3145502510607459335L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//
		String url = req.getHeader("Referer");
		// 로그아웃을 실행한 페이지 정보를 받아서 로그아웃을 실행하고 해당 페이지로 돌려보낸다.
		req.getSession().removeAttribute("loginId");
		resp.sendRedirect(url);
	}
}
