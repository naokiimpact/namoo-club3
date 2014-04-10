package com.namoo.club.dao.maria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.namoo.club.dao.CommunityDao;
import com.namoo.club.domain.Category;
import com.namoo.club.domain.Community;

public class CommunityDaoMaria implements CommunityDao {

	private ReturnResources returnResources = new ReturnResources();

	@Override
	public List<Community> readAllCommunities() {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Community> communities = new ArrayList<Community>();

		try {
			conn = DbConnection.getConnection();

			String sql = "SELECT a.comm_no, a.name, a.description, a.reg_date, b.category_no, b.category, c.user_id FROM community a INNER JOIN category b ON a.COMM_NO = b.COMM_NO INNER JOIN member c ON a.COMM_NO = c.GROUP_NO WHERE c.LEVEL = 3";
			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			Map<Integer, Category> categories = new HashMap<Integer, Category>();

			while (rset.next()) {

				int id = rset.getInt("comm_no");	// name

				String communityName = rset.getString("name");	// email
				String description = rset.getString("description"); // user password
				String admin = rset.getString("user_id");
				Date date = rset.getDate("reg_date");
				int categoryNo = rset.getInt("category_no");
				String category = rset.getString("category");


				Category categoryObj = new Category(id, category);
				categoryObj.setCategoryNo(categoryNo);
				categories.put(id, categoryObj);

				boolean check = true;
				for(Community community : communities){
					if(community.getId() == id){
						check = false;
					}
				}
				if(check == true){

					Community community = new Community(communityName, description);
					community.setDate(date);
					community.setId(id);
					community.setManager(admin);
					communities.add(community);
				}
			}

			Set<Entry<Integer, Category>> entrySet = categories.entrySet();
			Iterator<Entry<Integer, Category>> iter = entrySet.iterator();

			while(iter.hasNext()){
				Category category = iter.next().getValue();

				for(Community community : communities){
					if(community.getId() == category.getCommunityNo()){
						System.out.println("카테고리 : "+category.getCategory());
						System.out.println("커뮤니티  : "+community.getName());

						community.addCategory(category.getCategory());
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnResources.returnResources(rset, pstmt, conn);
		}
		return communities;
	}

	@Override
	public Community readCommunity(int communityNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Community community = null;
		try {
			conn = DbConnection.getConnection();

			String sql = "SELECT a.comm_no, a.name, a.description, a.reg_date, b.category_no, b.category, c.user_id FROM community a INNER JOIN category b ON a.COMM_NO = b.COMM_NO INNER JOIN member c ON a.COMM_NO = c.GROUP_NO WHERE c.LEVEL = 3 AND a.comm_no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, communityNo);
			rset = pstmt.executeQuery();
			
			int id = 0;
			String communityName  = null;
			String description = null;
			String admin = null;
			Date date = null;
			community = new Community();

//			Map<Integer, Category> categories = new HashMap<Integer, Category>();

			while (rset.next()) {

				id = rset.getInt("a.comm_no");	// name

				communityName = rset.getString("a.name");	// email
				description = rset.getString("a.description"); // user password
				admin = rset.getString("c.user_id");
				date = rset.getDate("a.reg_date");
				String category = rset.getString("b.category");
				community.addCategory(category);
//				if(rset.getString("b.category") != null){
//					String category = rset.getString("b.category");
//					Category categoryObj = new Category(id, category);
//					categoryObj.setCategoryNo(categoryNo);
//					categories.put(id, categoryObj);
//				}
				
				
			}
			community.setName(communityName);
			community.setDescription(description);
			community.setDate(date);
			community.setId(id);
			community.setManager(admin);

//			if(categories != null){
//
//				Set<Entry<Integer, Category>> entrySet = categories.entrySet();
//				Iterator<Entry<Integer, Category>> iter = entrySet.iterator();
//
//				while (iter.hasNext()) {
//					Category category = iter.next().getValue();
//					if (community.getId() == category.getCommunityNo()) {
//						System.out.println("카테고리 : " + category.getCategory());
//						System.out.println("커뮤니티  : " + community.getName());
//
//						community.addCategory(category.getCategory());
//					}
//				}
//			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnResources.returnResources(rset, pstmt, conn);
		}
		return community;
	}

	@Override
	public int createCommunity(Community community) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int communityNo = 0;
		try {
			conn = DbConnection.getConnection();

			String sql = "INSERT INTO community ( name, description ) VALUE ( ?, ? )";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, community.getName());
			pstmt.setString(2, community.getDescription());

			int count = pstmt.executeUpdate();

			rset = pstmt.getGeneratedKeys();
			if(rset.next()){
				communityNo = rset.getInt("comm_no");
				community.setId(communityNo);
			}
			System.out.println(count + "개의 쿼리가 실행되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnResources.returnResources(rset, pstmt, conn);
		}
		return communityNo;
	}

	@Override
	public void updateCommunity(Community community) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			conn = DbConnection.getConnection();

			String sql = "UPDATE community SET name = ? ,description = ? WHERE comm_no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, community.getName());
			pstmt.setString(2, community.getDescription());
			pstmt.setInt(3, community.getId());


			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 쿼리가 실행되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnResources.returnResources(rset, pstmt, conn);
		}
	}

	@Override
	public void deleteCommunity(int communityNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			conn = DbConnection.getConnection();

			String sql = "DELETE FROM community WHERE comm_no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, communityNo);


			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 쿼리가 실행되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			returnResources.returnResources(rset, pstmt, conn);
		}
	}

}
