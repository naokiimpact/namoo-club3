package com.namoo.club.web.controller.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/manage/community_modify.do")
public class CommunityModifyController extends HttpServlet {

	private static final long serialVersionUID = -4916959537266988019L;

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
		String description = req.getParameter("description");
		String name = req.getParameter("name");
		int commuityId = Integer.parseInt(req.getParameter("community_id"));

		System.out.println(name+"/"+commuityId + "/ description : "+description);

		CommunityService communityService = NamooClubServiceFactory.getInstance().getCommunityService();

		communityService.modifyCommunity(commuityId, name, description);

		resp.sendRedirect("community.do");
	}
}
