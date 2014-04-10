package com.namoo.club.dao.factory;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.maria.CategoryDaoMaria;
import com.namoo.club.dao.maria.MariaDBDaoFactory;
import com.namoo.club.dao.maria.MemberDaoMaria;
import com.namoo.club.dao.maria.UserDaoMaria;



public abstract class DaoFactory {

	public static enum DbType {
		MariaDB
		//,Oracle
	}

	protected DaoFactory(){
		//
	}

	public static DaoFactory createFactory(DbType dbType){
		//
		if (dbType == DbType.MariaDB) {
			return new MariaDBDaoFactory();
		} else {
			return null;
		}
	}

	public abstract CommunityDao getCommunityDaoMaria();

	public abstract ClubDao getClubDaoMaria();
	
	public abstract UserDaoMaria getUserDaoMaria();
	
	public abstract MemberDaoMaria getMemberDaoMaria();
	
	public abstract CategoryDaoMaria getCategoryDaoMaria();

}
