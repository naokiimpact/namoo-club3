package com.namoo.club.web.controller.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.service.facade.TownerService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/user/login.do")
public class LoginProcessController extends HttpServlet {

	private static final long serialVersionUID = -3654608386183372407L;

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
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");

		TownerService townerService = NamooClubServiceFactory.getInstance().getTownerService();
		boolean login = townerService.loginAsTowner(userId, password);
		if(login){
			String url = req.getParameter("url");
			if(url != null) {	// 로그인을 실행한 페이지 정보를 보냈는지 확인해서 로그인을 실행하고 해당 페이지로 돌려보낸다.
				System.out.println("로긴성공");
				req.getSession().setAttribute("loginId", userId);	// 로그인 유저 정보를 세션에 담아서 보냄 설정 
				resp.sendRedirect(url);	// 이전 주소로 리다이렉트
			} else {
				System.out.println("로긴성공");
				req.getSession().setAttribute("loginId", userId);
				resp.sendRedirect("../main.do");
			}
		}
		else{
			System.out.println("로긴실패");
//			RequestDispatcher dispatcher = req.getRequestDispatcher("../view/user/login.xhtml");
//			dispatcher.forward(req, resp);
			
			resp.sendRedirect("../view/user/login.xhtml");
		}
	}
}
