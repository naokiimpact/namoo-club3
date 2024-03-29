package com.namoo.club.service.logic;
import java.util.ArrayList;
import java.util.List;

import com.namoo.club.dao.CategoryDao;
import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.UserDao;
import com.namoo.club.dao.factory.DaoFactory;
import com.namoo.club.dao.factory.DaoFactory.DbType;
import com.namoo.club.domain.Category;
import com.namoo.club.domain.Club;
import com.namoo.club.domain.Community;
import com.namoo.club.domain.Member;
import com.namoo.club.domain.SocialPerson;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.logic.exception.NamooExceptionFactory;

public class CommunityServiceLogic implements CommunityService {

    private CommunityDao communityDao;
    private MemberDao memberDao;
    private UserDao userDao;
    private CategoryDao categoryDao;
    private ClubDao clubDao;

    public CommunityServiceLogic() {
        //
        communityDao = DaoFactory.createFactory(DbType.MariaDB).getCommunityDaoMaria();
        memberDao = DaoFactory.createFactory(DbType.MariaDB).getMemberDaoMaria();
        userDao = DaoFactory.createFactory(DbType.MariaDB).getUserDaoMaria();
        categoryDao = DaoFactory.createFactory(DbType.MariaDB).getCategoryDaoMaria();
        clubDao = DaoFactory.createFactory(DbType.MariaDB).getClubDaoMaria();
    }

    @Override
    public void registCommunity(String communityName, String description, String email, List<String> categories) {
        //

        List<Community> communities = communityDao.readAllCommunities();
        for(Community community : communities){
            if(community.getName().equals(communityName)){
                throw NamooExceptionFactory.createRuntime("이미 존재하는 커뮤니티입니다.");
            }
        }

        SocialPerson towner = userDao.readUser(email);
        if (towner == null) {
            throw NamooExceptionFactory.createRuntime("존재하지 않는 주민입니다.");
        }


        Community com = new Community(communityName, description);
        //com.setCategories(categories);
        int communityNo = communityDao.createCommunity(com);

        Member member = new Member(email, communityNo, 1, 3);
        memberDao.createMember(member);

        for(String category : categories){
        	Category categoryObj = new Category(communityNo, category);
        	categoryDao.createCategory(categoryObj);
        }
    }

    @Override
    public Community findCommunity(int communityNo){
        //
    	Community community = communityDao.readCommunity(communityNo);
    	List<Member> members = memberDao.readMemberByGroup(communityNo, 1);
    	for(Member member : members){
    		community.addMember(member.getEmail());
    	}
    	List<Club> clubs = clubDao.readAllClubByCommunityId(community.getId());
		for (Club club : clubs){
			community.addClub(club);
		}

    	return community;
    }



    @Override
    public void joinAsMember(int communityNo, String email) {
        //
        Community community = findCommunity(communityNo);
        if (community == null) {
            throw NamooExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
        }

        SocialPerson towner = userDao.readUser(email);
        if (towner == null) {
            throw NamooExceptionFactory.createRuntime("존재하지 않는 주민입니다.");
        }

        Member member = new Member(email, communityNo, 1, 1);
        memberDao.createMember(member);
    }

    @Override
    public Member findCommunityMember(int communityNo, String email) {
        //
    	List<Member> members = memberDao.readMemberByGroup(communityNo, 1);

        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public List<Member> findAllCommunityMember(int communityNo) {
        //
        return memberDao.readMemberByGroup(communityNo, 1);
    }

   /* @Override
    public int countMembers(int communityNo){
        //
        return memberDao.readMemberByGroup(communityNo, 1).size();
    }*/

    @Override
    public void removeCommunity(int communityNo) {
        //
    	List<Category> categories = categoryDao.readCategoriesByCommunityNo(communityNo);
    	for (Category category : categories) {
    		categoryDao.deleteCategory(category.getCategoryNo());
		}
    	List<Member> memberList = memberDao.readMemberByGroup(communityNo, 1);
    	for (Member member : memberList) {
    		memberDao.deleteMember(member);
		}
    	communityDao.deleteCommunity(communityNo);
    }

    @Override
    public List<Community> findAllCommunities() {
        //
    	List<Community> communities = communityDao.readAllCommunities();
    	for (Community community : communities){
    		List<Member> members = memberDao.readMemberByGroup(community.getId(), 1);
    		List<Club> clubs = clubDao.readAllClubByCommunityId(community.getId());
    		for (Club club : clubs){
    			community.addClub(club);
    		}
    		for(Member member : members){
        		community.addMember(member.getEmail());
        	}
    	}
        return communities;
    }

    @Override
    public List<Community> findBelongCommunities(String email) {
        //
        List<Community> commnities = findAllCommunities();
        if (commnities == null) return null;

        List<Member> members = memberDao.readMembersByPerson(email);
        List<Community> belongs = new ArrayList<Community>();

        for (Member member : members){
        	if(member.getGroupType() == 1){
        		Community community = findCommunity(member.getGroupNo());
        		belongs.add(community);
        	}
        }
        return belongs;
    }

    @Override
    public List<Community> findManagedCommnities(String email) {
        //
    	List<Community> commnities = findAllCommunities();
        if (commnities == null) return null;

        List<Member> members = memberDao.readMembersByPerson(email);
        List<Community> manages = new ArrayList<Community>();

        for (Member member : members){
        	if(member.getGroupType() == 1 && member.getLevel() == 3){
        		Community community = findCommunity(member.getGroupNo());
        		manages.add(community);
        	}
        }
        return manages;
    }

    @Override
    public void withdrawalCommunity(int communityNo, String email) {
        //
        Community community = findCommunity(communityNo);
        if (community == null) {
            throw NamooExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
        }




        List<Member> members = memberDao.readMemberByCommunity(email, communityNo);

        for (Member member2 : members){
       	 if(member2.getLevel()==3) {
       		throw NamooExceptionFactory.createRuntime("클럽 관리자는 해당 커뮤니티를 탈퇴할 수 없습니다.");
       	 }
       }

        for (Member member2 : members){
        	 memberDao.deleteMember(member2);
        }
        Member member = new Member(email, communityNo, 1);
        memberDao.deleteMember(member);
    }



    @Override
    public void modifyCommunity(int communityNo, String communityName, String description) {
        //
        Community community = findCommunity(communityNo);
        community.setName(communityName);
        community.setDescription(description);

        communityDao.updateCommunity(community);
    }
}
