package com.namoo.club.domain;


public class ClubMember {

	private int clubNo;
	private String email;

	//--------------------------------------------------------------------------
	// constructor

	/**
	 *
	 * @param rolePerson
	 */
	public ClubMember(int clubNo, String email){
		//
		this.clubNo = clubNo;
		this.email = email;
	}

	//--------------------------------------------------------------------------
	// getter/setter

	public String getEmail() {
		return email;
	}

}