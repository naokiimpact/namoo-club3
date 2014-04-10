package com.namoo.club.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Club {

	private int communityId;
	private int id;
	private String name;
	private String description;

	private List<ClubManager> managers;
	private List<ClubMember> members;

	private ClubManager mainManager;

	private String category;
	private Date date;

	//--------------------------------------------------------------------------
	// constructors

	/**
	 *
	 * @param clubName
	 * @param admin
	 */
	public Club(int id, String clubName, String description, String email, String category, Date date){
		//
		this.id = id;
		this.name = clubName;
		this.description = description;
		this.members = new ArrayList<ClubMember>();
		this.managers = new ArrayList<ClubManager>();
		this.category = category;
		this.date = date;

		/*setManager(email);
		addMember(email);*/

	}

	public Club(int id, String clubName, String description, String email, String category){
		//
		this.id = id;
		this.name = clubName;
		this.description = description;
		this.members = new ArrayList<ClubMember>();
		this.managers = new ArrayList<ClubManager>();
		this.category = category;

		/*setManager(email);
		addMember(email);*/
	}

	public Club(String clubName, String description, String email, String category){
		//
		this.name = clubName;
		this.description = description;
		this.members = new ArrayList<ClubMember>();
		this.managers = new ArrayList<ClubManager>();
		this.category = category;

		/*setManager(email);
		addMember(email);*/

	}

	public Club(int id, String clubName, String description) {
		//
		this.id = id;
		this.name = clubName;
		this.description = description;
		this.members = new ArrayList<ClubMember>();
		this.managers = new ArrayList<ClubManager>();

	}

	public Club(String clubName, String description) {
		//
		this.name = clubName;
		this.description = description;
		this.members = new ArrayList<ClubMember>();
		this.managers = new ArrayList<ClubManager>();

	}

	//--------------------------------------------------------------------------
	// getter/setter

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ClubManager> getManager() {
		return managers;
	}

	public ClubManager getMainManager() {
		for (ClubManager manager : managers){
			if (manager.getLevel() == 3){
				mainManager = manager;
				return mainManager;
			}
		}
		return null;
	}

	public List<ClubMember> getMembers() {
		return members;
	}

	//--------------------------------------------------------------------------
	// public methods

	public ClubMember findMember(String email) {
		//
		for (ClubMember member : members) {
			if(member.equals(email)) {
				return member;
			};
		}
		return null;
	}


	public void addManager(String email, int level){
		//
		ClubManager manager = new ClubManager(id, email, level);
		this.managers.add(manager);
	}



	public void addMember(String email){
		//
		ClubMember member = new ClubMember(id, email);
		this.members.add(member);
	}


	public void removeMember(String email) {
		//
		ClubMember found = null;
		for (ClubMember member : members) {
			if (member.equals(email)) {
				found = member;
			}
		}
		if (found != null) {
			members.remove(found);
		}
	}

	@Override
	public boolean equals(Object obj) {
		//
		Club club = (Club) obj;
		if(club.getId() == this.id)
			return true;
		return false;
	}

}
