package com.namoo.club.web.controller.manage;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.namoo.club.domain.Club;
import com.namoo.club.domain.ClubManager;
import com.namoo.club.domain.Community;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/manage/club.do")
public class ClubManageController extends HttpServlet{

	private static final long serialVersionUID = -7828472142558524181L;

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
		CommunityService communityService = NamooClubServiceFactory.getInstance().getCommunityService();

		HttpSession session = req.getSession();
		String email = (String) session.getAttribute("loginId");

		List<Community> myCommunityList = communityService.findBelongCommunities(email);
		List<Club> clubList = clubService.findManagedClubs(email);

		req.setAttribute("community_list", myCommunityList);
		req.setAttribute("club_list", clubList);


		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/manage/club.jsp");
		dispatcher.forward(req, resp);
	}

}
