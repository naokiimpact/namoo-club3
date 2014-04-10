package com.namoo.club.web.controller.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/community/remove.do")
public class CommunityRemoveProcessController extends HttpServlet{
	
	private static final long serialVersionUID = -3205123331812122661L;

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
		CommunityService communityService = NamooClubServiceFactory.getInstance().getCommunityService();

		int communityId = Integer.parseInt(req.getParameter("community_id"));
		
		communityService.removeCommunity(communityId);;
		
		resp.sendRedirect("../main.do");
	}
}