package com.namoo.club.web.controller.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.domain.Club;
import com.namoo.club.domain.Member;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/manage/club_mem.do")
public class ClubMemberModifyController extends HttpServlet {

	private static final long serialVersionUID = 595711605080277926L;

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
		CommunityService communityService = NamooClubServiceFactory.getInstance().getCommunityService();

		String clubId = req.getParameter("club_id"); //아직 안받아왔음

		System.out.println("level : "+req.getParameter("level"));
		int level = Integer.parseInt(req.getParameter("level"));

		Club club = clubService.findClub(Integer.parseInt(clubId));

		String[] members = req.getParameterValues("checked_member");



		clubService.countManagers(Integer.parseInt(clubId), members.length);

		for(String member : members){
			clubService.modifyManager(Integer.parseInt(clubId), member, level);
		}
		RequestDispatcher dispatcher;
		if(level==3){
			clubService.modifyManager(Integer.parseInt(clubId), (String) req.getSession().getAttribute("loginId"), 1);
			dispatcher = req.getRequestDispatcher("/main.do");
			//dispatcher.forward(req, resp);
		}
		else{
			dispatcher = req.getRequestDispatcher("club_mem?club_id="+clubId);
		}
		dispatcher.forward(req, resp);
	}
}
