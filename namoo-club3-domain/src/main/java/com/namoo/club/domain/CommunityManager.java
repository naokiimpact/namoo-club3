package com.namoo.club.domain;


public class CommunityManager {

	private int communityNo;
	private String email;

	//--------------------------------------------------------------------------
	// constructor

	/**
	 *
	 * @param rolePerson
	 */
	public CommunityManager(int communityNo, String email){
		//
		this.communityNo = communityNo;
		this.email = email;
	}

	//--------------------------------------------------------------------------
	// getter/setter

	public String getEmail() {
		//
		return email;
	}

}