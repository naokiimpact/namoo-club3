package com.namoo.club.dao.maria;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.namoo.club.dao.maria.CommunityDaoMaria;
import com.namoo.club.domain.Community;

public class CommunityDaoMariaTest {

	CommunityDaoMaria dao = null;
	String communityName = "테스트커뮤니티";
	String description ="테스트테스트설명입니다";
	int communityNo;


	@Before
	public void setUp() throws Exception {
		dao = new CommunityDaoMaria();
	}

	@After
	public void tearDown() throws Exception {
		//dao.deleteCommunity(communityNo);
	}

	@Test
	public void testReadAllCommunities() {
		int size = dao.readAllCommunities().size();

		assertEquals(1, size);
	}



	@Test
	public void testCreateCommunity() {
		communityNo = createCommunity();
		Community community = dao.readCommunity(communityNo);
		assertEquals(communityName, community.getName());
	}

	public int createCommunity() {
		Community community = new Community(communityName, description);
		return dao.createCommunity(community);
	}

	@Test
	public void testUpdateCommunity() {
		communityNo = createCommunity();
		Community community = dao.readCommunity(communityNo);

		if(community ==null){
			System.out.println("null");
		}
		System.out.println(communityNo);
		community.setDescription("바꿈 ㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
		dao.updateCommunity(community);
		community = dao.readCommunity(communityNo);
		assertEquals("바꿈 ㅋㅋㅋㅋㅋㅋㅋㅋㅋ", community.getDescription());
	}

	@Test
	public void testDeleteCommunity() {
		dao.deleteCommunity(2);
	}

}
