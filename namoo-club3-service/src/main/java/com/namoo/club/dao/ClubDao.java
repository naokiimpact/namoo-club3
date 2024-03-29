package com.namoo.club.dao;

import java.util.List;
import java.util.Map;

import com.namoo.club.domain.Club;
import com.namoo.club.domain.Member;

public interface ClubDao {
	//
	List<Club> readAllClubByCommunityId(int communityId);

	Club readClub(int id);

	List<Member> readAllClubMembersById(int id);


	int createClub(int communityNo, Club club, int categoryNo);


	void updateClub (Club club);


	void deleteClub (int id);

}
