<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head
	th:replace="common/common_head :: common_head(${htmlTitle},~{},~{})">
</head>
<body class="flat-blue body-container">
	<div class="panel panel-default main-panel">
		<div class="panel-heading main-panel-head">
			<h3 class="panel-title">${menuName}</h3>
		</div>
		<div class="panel-body main-panel-body">
			<div class="search-condition-container">
				<form id="searchForm" class="form-inline" role="form">
					<div class="form-group">
						<label for="" class="control-label">Label</label> 
						<input type="text" class="form-control" id="" name="" placeholder="请输入Label">
					</div>
					<div th:replace="common/search_button"></div>
				</form>
			</div>
			<div id="toolbar" style="margin-left: 5px;">
				<div th:replace="common/toolbar_button"></div>
			</div>
			<div class="content-container-table">
				<table id="table"
					class="table table-striped table-hover table-condensed"></table>
			</div>
		</div>
	</div>
	<div th:replace="common/common_foot :: common_foot(~{::script})">
		<!-- Javascript -->
		<script type="text/javascript" th:src="@{/page/${modelName}/js/${modelName}List.js}"></script>
	</div>
</body>