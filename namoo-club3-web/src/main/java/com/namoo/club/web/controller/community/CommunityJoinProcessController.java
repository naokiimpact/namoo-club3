package com.namoo.club.web.controller.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.factory.NamooClubServiceFactory;
import com.namoo.club.web.controller.annotation.LoginRequired;

@WebServlet("/community/join.do")
@LoginRequired
public class CommunityJoinProcessController extends HttpServlet {

	private static final long serialVersionUID = 5537704466401117989L;

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
		int communityId = Integer.parseInt(req.getParameter("community_id"));

		CommunityService communityService = NamooClubServiceFactory.getInstance().getCommunityService();

		String loginId = (String) req.getSession().getAttribute("loginId");

		communityService.joinAsMember(communityId, loginId);
		resp.sendRedirect("../main.do");
	}
}
