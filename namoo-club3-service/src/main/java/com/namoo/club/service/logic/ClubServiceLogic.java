package com.namoo.club.service.logic;

import java.util.ArrayList;
import java.util.List;

import com.namoo.club.dao.CategoryDao;
import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.factory.DaoFactory;
import com.namoo.club.dao.factory.DaoFactory.DbType;
import com.namoo.club.domain.Category;
import com.namoo.club.domain.Club;
import com.namoo.club.domain.Member;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.logic.exception.NamooExceptionFactory;

public class ClubServiceLogic implements ClubService {

	private ClubDao clubDao;
	private MemberDao memberDao;
	private CategoryDao categoryDao;

	public ClubServiceLogic() {
		//
		this.clubDao = DaoFactory.createFactory(DbType.MariaDB).getClubDaoMaria();
		this.memberDao = DaoFactory.createFactory(DbType.MariaDB).getMemberDaoMaria();
		this.categoryDao = DaoFactory.createFactory(DbType.MariaDB).getCategoryDaoMaria();
	}

	@Override
	public void registClub(int communityNo, String email, Club club, String category) {
		//
		List<Category> categories = categoryDao.readCategoriesByCommunityNo(communityNo);
		int categoryNo = 0;
		for (Category cate : categories) {
			if(cate.getCategory().equals(category)){
				categoryNo = cate.getCategoryNo();
			}
		}

		int id = clubDao.createClub(communityNo, club, categoryNo);
		Member member = new Member(email, id, 2, 3);
		memberDao.createMember(member);
	}

	@Override
	public List<Club> findAllClubs(int communityNo) {

		List<Club> clubList = clubDao.readAllClubByCommunityId(communityNo);


		for (Club club : clubList){
			List<Member> members = memberDao.readMemberByGroup(club.getId(), 2);

			for (Member member : members){
				//System.out.println(club.getName() + " / "+member.getEmail());
				if(member.getLevel()==1){
					//System.out.println("멤버 : "+club.getName() + " / "+member.getEmail());
					club.addMember(member.getEmail());
				}
				else {
					//System.out.println("매니저 : "+club.getName() + " / "+member.getEmail());
					club.addManager(member.getEmail(), member.getLevel());
					club.addMember(member.getEmail());
				}
			}
		}

		/*for (Club club : clubList){
			System.out.println("----------------------------------------");
			System.out.println("클럽이름 : "+club.getName());
			System.out.println("매니저 : "+club.getManager().getEmail());
			System.out.println("멤버 : "+club.getMembers().size());
		}*/

		return clubList;
	}

	@Override
	public Club findClub(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		List<Member> members = memberDao.readMemberByGroup(clubNo, 2);
		for (Member member : members){
			//System.out.println(club.getName() + " / "+member.getEmail());
			if(member.getLevel()==1){
				//System.out.println("멤버 : "+club.getName() + " / "+member.getEmail());
				club.addMember(member.getEmail());
			}
			else {
				//System.out.println("매니저 : "+club.getName() + " / "+member.getEmail());
				club.addManager(member.getEmail(), member.getLevel());
				club.addMember(member.getEmail());
			}
		}
		return club;
	}


	@Override
	public void joinAsMember(int clubNo, String email) {
		//
		int communityId = clubDao.readClub(clubNo).getCommunityId();
		List<Member> mem = memberDao.readMembersByPerson(email);
		for (Member member : mem){
			if(member.getGroupType()==1 && member.getGroupNo()==communityId){
				member = new Member(email, clubNo, 2, 1);
				memberDao.createMember(member);
				return;
			}
		}
		throw NamooExceptionFactory.createRuntime("커뮤니티에 가입 후 소속 클럽에 가입하실 수 있습니다.");
	}


	@Override
	public List<Member> findAllClubMember(int clubNo) {
		//
		Club club = findClub(clubNo);

		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return clubDao.readAllClubMembersById(clubNo);
	}

	/*@Override
	public int countMembers(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);

		if (club != null) {
			return clubDao.readAllClubMembersById(clubNo).size();
		}

		return 0;
	}*/

	@Override
	public void removeClub(int clubNo) {
		//
		List<Member> memberList = memberDao.readMemberByGroup(clubNo, 2);
    	for (Member member : memberList) {
    		memberDao.deleteMember(member);
		}
		clubDao.deleteClub(clubNo);
	}

	@Override
	public List<Club> findBelongClubs(int communityNo, String email) {
		//
		List<Member> memberList = memberDao.readMembersByPerson(email);
		List<Club> BelongClubs = new ArrayList<Club>();

		System.out.println("member : "+memberList.size());

		for (Member member : memberList) {
			if(member.getGroupType() == 2){
				Club club = clubDao.readClub(member.getGroupNo());
				if(club.getCommunityId() == communityNo){

					List<Member> members = memberDao.readMemberByGroup(club.getId(), 2);
					for (Member member2 : members){
						if(member2.getLevel()==1){
							club.addMember(member2.getEmail());
						}
						else{
							club.addManager(member2.getEmail(), member2.getLevel());
							club.addMember(member2.getEmail());
						}
					}
					System.out.println(club.getManager().get(0).getEmail());
					BelongClubs.add(club);/////여기에서 에러가 나던지 아니면 BelongclUBS를 사용하는 과정에서 에러가 나던지 둘 중 하나
				}
			}
		}
		return BelongClubs;
	}

	@Override
	public List<Club> findManagedClubs(String email) {
		//
		List<Member> memberList = memberDao.readMembersByPerson(email);
		List<Club> ManagedClubs = new ArrayList<Club>();
		for (Member member : memberList) {
			if(member.getGroupType() == 2 && member.getLevel() == 3){
				Club club = clubDao.readClub(member.getGroupNo());
				List<Member> members = memberDao.readMemberByGroup(club.getId(), 2);
				for (Member member2 : members){
					if(member2.getLevel()==1){
						club.addMember(member2.getEmail());
					}
					else{
						club.addManager(member.getEmail(), member.getLevel());
						club.addMember(member2.getEmail());
					}
				}
				ManagedClubs.add(club);
			}
		}
		return ManagedClubs;
	}

//	@Override
//	public List<Club> findMadeClubs(String email) {
//		//
//		List<Member> memberList = memberDao.readMembersByPerson(email);
//		List<Club> MadeClubs = new ArrayList<Club>();
//		for (Member member : memberList) {
//			if(member.getGroupType() == 2 && member.getLevel() == 3){
//				Club club = clubDao.readClub(member.getGroupNo());
//					MadeClubs.add(club);
//			}
//		}
//		return MadeClubs;
//	}

	@Override
	public void withdrawalClub(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		Member member = new Member(email, clubNo, 2);

		memberDao.deleteMember(member);
	}

	@Override
	public void modifyClub(int clubNo, String name, String description) {
		//
		Club club = new Club(clubNo, name, description);
		clubDao.updateClub(club);
	}

	@Override
	public void modifyManager(int clubNo, String email, int level) {
		//
		Member member = memberDao.readMember(email, clubNo, 2);
		member.setLevel(level);
		memberDao.updateMember(member);
	}

	@Override
	public int countManagers(int clubNo, int managerNum) {
		//
		
		int count = memberDao.findManager(clubNo, 2).size() + managerNum;
		if(count > 5){
			throw NamooExceptionFactory.createRuntime("관리자는 5명까지 가능합니다.");
		}
		return count;
	}
}
