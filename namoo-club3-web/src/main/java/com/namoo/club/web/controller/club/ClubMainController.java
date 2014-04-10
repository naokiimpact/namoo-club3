package com.namoo.club.web.controller.club;

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

@WebServlet("/club/main.do")
public class ClubMainController extends HttpServlet {

	private static final long serialVersionUID = -4246358558327339075L;

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
		int id = Integer.parseInt(req.getParameter("club_id"));

		Club club = clubService.findClub(id);

		req.setAttribute("club", club);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/club/main.jsp");
		dispatcher.forward(req, resp);
	}
}
