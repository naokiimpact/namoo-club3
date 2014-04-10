package com.namoo.club.domain;


public class ClubManager {

	private int clubNo;
	private String email;
	private int level;

	//--------------------------------------------------------------------------
	// constructor

	/**
	 *
	 * @param rolePerson
	 */
	public ClubManager(int clubNo, String email, int level){
		//
		this.clubNo = clubNo;
		this.email = email;
		this.level = level;
	}

	//--------------------------------------------------------------------------
	// getter/setter

	public String getEmail() {
		//
		return email;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


}
