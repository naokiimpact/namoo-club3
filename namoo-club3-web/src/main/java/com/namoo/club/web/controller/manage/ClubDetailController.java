package com.namoo.club.web.controller.manage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.namoo.club.domain.Club;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.factory.NamooClubServiceFactory;

@WebServlet("/manage/club_detail.do")
public class ClubDetailController extends HttpServlet {

	private static final long serialVersionUID = 2123053941180983076L;

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
		int club_id = Integer.parseInt(req.getParameter("club_id"));

		ClubService clubService = NamooClubServiceFactory.getInstance().getClubService();

		Club club = clubService.findClub(club_id);


		req.setAttribute("club", club);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/manage/club_detail.jsp");
		dispatcher.forward(req, resp);
	}

}
