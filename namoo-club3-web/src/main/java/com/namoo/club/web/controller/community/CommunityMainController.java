package com.namoo.club.web.controller.community;

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

@WebServlet("/community/main.do")
public class CommunityMainController extends HttpServlet {

	private static final long serialVersionUID = 8075594201606793335L;

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
		ClubService clubService = NamooClubServiceFactory.getInstance().getClubService();

		HttpSession session = req.getSession();

		String email = (String) session.getAttribute("loginId");
		System.out.println(email);

		int communityId = Integer.parseInt(req.getParameter("community_id"));


		List<Club> clubList = clubService.findAllClubs(communityId);

		List<Club> notMyclubList = clubService.findAllClubs(communityId);


		req.setAttribute("community_id", communityId);

		Community community = communityService.findCommunity(communityId);

		req.setAttribute("community", community);

		List<Club> myClubList = clubService.findBelongClubs(communityId, email);


		if (email == null) {
			req.setAttribute("club_list", clubList);
		} else {
			for(int i = notMyclubList.size()-1; i>=0 ; i--){
				if(myClubList.contains(notMyclubList.get(i))){
					notMyclubList.remove(i);
				}
			}
			req.setAttribute("my_club_list", myClubList);
			req.setAttribute("not_my_club_list", notMyclubList);
		}

		for (Club club : myClubList){
			List<ClubManager> managers = club.getManager();
			for (ClubManager manager : managers){
				System.out.println("매니저들 : "+manager.getEmail()+"/"+manager.getLevel());
			}
		}


		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/community/main.jsp");
		dispatcher.forward(req, resp);
	}
}
