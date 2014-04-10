package com.namoo.club.web.controller.club;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/club/remove.do")
public class ClubRemoveController extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 720645155254442304L;

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
		ClubService clubService = NamooClubServiceFactory.getInstance().getClubService();

		String communityId = req.getParameter("community_id");
		int clubId = Integer.parseInt(req.getParameter("club_id"));

		clubService.removeClub(clubId);

		resp.sendRedirect("../community/main.do?community_id="+communityId);
	}

}
