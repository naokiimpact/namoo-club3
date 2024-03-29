<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="../common/head.jsp"%>


<title>나무커뮤니티</title>

<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
						<h2>클럽 정보 수정</h2>

					</div>
				</div>
			</div>
		</div>
	</header>

	<!-- Container ======================================================================================= -->
	<div class="container">
		<div class="row">
			<div class="col-lg-12">




				<div class="well">
					<p></p>
					<form class="form-horizontal" action="${ctx}/manage/club_modify.do"
						method="post">
						<fieldset>
							<div class="form-group">
								<label class="col-lg-2 control-label">클럽 이름</label>

								<div class="col-lg-10">
									<input type="text" name="name" class="form-control"
										value="${club.name}" required> <input type="hidden"
										name="club_id" value="${param.club_id}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">대표 관리자</label>

								<div class="col-lg-10">
									<input type="text" name="email" class="form-control"
										value="${club.mainManager.email}" readOnly>

								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">설명</label>

								<div class="col-lg-10">
									<textarea name="description" rows="10" cols="80"
										class="form-control" required>${club.description}</textarea>

								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-10 col-lg-offset-2">
									<button type="submit" class="btn btn-primary">수정</button>
									<button class="btn btn-default"
										onclick="history.back(); return false;">취소</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>

		</div>

		<!-- Footer ========================================================================================== -->
		<%@include file="../common/footer.jsp"%>
	</div>
</body>
</html>