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

@WebServlet("/manage/club_mem")
public class ClubMemberController extends HttpServlet {

	private static final long serialVersionUID = -1443712840234973243L;

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

		Club club = clubService.findClub(Integer.parseInt(clubId));
		//List<ClubMember> members = club.getMembers();

		List<Member> allMembers = clubService.findAllClubMember(Integer.parseInt(clubId));
		List<Member> managers = new ArrayList<Member>();
		List<Member> members = new ArrayList<Member>();

		for(Member member : allMembers){
			if(member.getLevel()==1){
				members.add(member);
			}
			else{
				managers.add(member);
			}
		}

		req.setAttribute("managers", managers);
		req.setAttribute("members", members);


		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/manage/club_mem.jsp");
		dispatcher.forward(req, resp);
	}

}
