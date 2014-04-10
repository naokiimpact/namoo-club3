package com.namoo.club.dao;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.namoo.club.dao.maria.CategoryDaoMaria;
import com.namoo.club.dao.maria.ClubDaoMaria;
import com.namoo.club.domain.Club;

public class ClubDaoTest {
	
	ClubDao clubDao = new ClubDaoMaria(); 
	CategoryDao cateDao= new CategoryDaoMaria();
	
	private int communityId = 1;
	private int id = 9;
	
	private String clubName = "테스트"; 
	private String description = "테스트 클럽입니다."; 
	private String email = "kim"; 
	private String category = "자바6"; 
	
	Club club = new Club(clubName, description, email, category);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadAllClubByCommunityId() {
		clubDao.readAllClubByCommunityId(communityId);
	}

	@Test
	public void testCreateClub() {
		clubDao.createClub(communityId, club, 6);
	}
	
	@Test
	public void testUpdateClub() {
		Club club = new Club(id, clubName, description, email, category);
		clubDao.updateClub(club);
	}
	
	@Test
	public void testReadClub() {
		clubDao.readClub(id);
	}

	@Test
	public void testReadAllClubMembersById() {
		clubDao.readAllClubMembersById(id);
	}
	
	@Test
	public void testDeleteClub() {
		clubDao.deleteClub(id);
	}

}
