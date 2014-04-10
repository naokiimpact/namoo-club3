<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@include file="../common/head.jsp"%>
    <title>나무커뮤니티</title>
<script type="text/javascript">
function levelCheck(id) {
	document.getElementById("level").value=id.value;
}
</script>
</head>
<body>

<!-- Main Navigation ========================================================================================== -->
<%@include file="../common/main_navi.jsp"%>

<!-- Header ========================================================================================== -->
<header>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="jumbotron">
                    <h2>커뮤니티 관리센터</h2>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12 col-lg-12">
                <ol class="breadcrumb">
                    <li><a href="#">관리자 홈</a></li>
                    <li><a href="#">커뮤니티 관리</a></li>
                    <li class="active">회원관리</li>
                </ol>
            </div>
        </div>
    </div>
</header>

<!-- Container ======================================================================================= -->
<div class="container">
    <div class="row">


        <!-- ★★★ Contents -->
        <div class="col-sm-9 col-lg-9">
            <div class="page-header2">
                <h3>회원관리</h3>
            </div>

            <!-- ★★★ Tab Menu -->
            <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li class="active"><a href="#all" data-toggle="tab">전체회원</a></li>

            </ul>


            <!-- ★★★ Tab Panel -->
            <div id="communityList" class="tab-content">
                <!-- ★★★ 전체회원 -->
                <div class="tab-pane fade active in" id="all">

                    <!-- ★★★ 검색조건 -->
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form class="form-search">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="회원명 또는 ID">
                                    <span class="input-group-btn">
                                        <button type="submit" class="btn btn-primary">검색</button>
                                    </span>
                                </div>
                            </form>
                        </div>
                    </div>


					<div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">ID</th>
                                <th class="text-center">레벨</th>
                                <!-- <th class="text-center">권한부여</th> -->
                            </tr>
                            </thead>
								<tbody>
									<!--<tr><td colspan="5" class="text-center">가입한 회원이 없습니다.</td></tr>-->
									<c:forEach var="manager" items="${managers}" varStatus="managerCount">
										<tr>
											<td class="text-center">${managerCount.count}</td>
											<td>${manager.email}</td>
											<td class="text-center">${manager.level}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
                    </div>


                    <!-- ★★★ 회원목록 -->
                    <div class="table-responsive">

                    <form action="${ctx}/manage/club_mem.do" method="post">
                    	<input type="hidden" name="club_id" value="${param.club_id}">
						<input type="hidden" id="level" name="level" value="0">
						<c:if test="${members.size()!=0}">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">ID</th>
                                <th class="text-center">레벨</th>
                                <th class="text-center">권한부여</th>
                            </tr>
                            </thead>
								<tbody>
									<!--<tr><td colspan="5" class="text-center">가입한 회원이 없습니다.</td></tr>-->
									<c:forEach var="member" items="${members}" varStatus="managerCount">
										<tr>
											<td class="text-center">${managerCount.count}</td>
											<td>${member.email}</td>
											<%-- <td><a href="./commViewMember.html">${member.name}</a></td> --%>
											<td class="text-center">${member.level}</td>
											<td class="text-center"><input type="checkbox" name="checked_member" value="${member.email}"></td>
											<%-- <c:choose>
												<c:when test="${member.level==1}">
													<th class="text-center"><button class="btn btn-default" onclick=location.href="${ctx}/manage/club_mem.do?club_id=${club.id}">관리자 권한 부여</button></th>
												</c:when>
												<c:otherwise>
													<th class="text-center"><button class="btn btn-default" onclick=location.href="${ctx}/manage/club_mem.do?club_id=${club.id}">관리자 권한 박탈</button></th>
												</c:otherwise>
											</c:choose> --%>
										</tr>
									</c:forEach>
								</tbody>
							</table>
								<th class="text-right"><button class="btn btn-default" value="3" onclick="levelCheck(this)">대표 관리자</button></th>
								<th class="text-right"><button class="btn btn-default" value="2" onclick="levelCheck(this)">서브 관리자</button></th>
							</c:if>
                    </form>
                    </div>
                </div>

                    <div class="text-center">

                    </div>
                </div>
            </div>

        </div>
        <!-- Footer ========================================================================================== -->
   		<%@include file="../common/footer.jsp"%>
    </div>
</body>
</html>