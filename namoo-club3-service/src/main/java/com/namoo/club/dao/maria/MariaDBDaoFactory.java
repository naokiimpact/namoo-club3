package com.namoo.club.dao.maria;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.factory.DaoFactory;



public class MariaDBDaoFactory extends DaoFactory {

	@Override
	public CommunityDao getCommunityDaoMaria() {
		//
		return new CommunityDaoMaria();
	}

	@Override
	public ClubDao getClubDaoMaria() {
		//
		return new ClubDaoMaria();
	}
	
	@Override
	public UserDaoMaria getUserDaoMaria() {
		//
		return new UserDaoMaria();
	}

	@Override
	public MemberDaoMaria getMemberDaoMaria() {
		//
		return new MemberDaoMaria();
	}

	@Override
	public CategoryDaoMaria getCategoryDaoMaria() {
		//
		return new CategoryDaoMaria();
	}
}
