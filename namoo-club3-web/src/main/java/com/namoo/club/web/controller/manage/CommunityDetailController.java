package com.namoo.club.web.controller.manage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.domain.Community;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/manage/community_detail.do")
public class CommunityDetailController extends HttpServlet {

	private static final long serialVersionUID = -6076692222658520079L;

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
		int community_id = Integer.parseInt(req.getParameter("community_id"));

		CommunityService communityService = NamooClubServiceFactory.getInstance().getCommunityService();

		Community community = communityService.findCommunity(community_id);

		req.setAttribute("community", community);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/manage/community_detail.jsp");
		dispatcher.forward(req, resp);
	}

}
