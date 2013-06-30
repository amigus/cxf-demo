<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html data-ng-app="content-ui">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII" />
		<title>Content UI</title>
		<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="css/index.css"/>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.1.5/angular.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.1.5/angular-resource.js"></script>
		<script type="text/javascript" src="js/index.js"></script>
	</head>
	<body data-ng-controller="ContentController">
		<form id="author-form" name="authorForm">
			<div id="author">
				<label id="author-label">Author</label>
				<input data-ng-model="author" id="author-input" name="author"
						type="text" placeholder="who are you?" required />
				<div id="author-form-errors" data-ng-show="authorForm.$invalid">
					<span class="author-form-errors"
							data-ng-show="authorForm.author.$error.required">
								(required to add content)
					</span>
				</div>
			</div>
		</form>
		<h1>Content UI</h1>
		<form id="content-form" name="contentForm">
			<div id="content-editor" title="Content">
					<div id="edit-content-title">
						<div id="edit-content-title-label">Title:</div>
						<input data-ng-model="current.title" id="edit-content-title-input"
								name="title" required type="text"/>
					</div>
					<div id="edit-content-text">
						<div id="edit-content-text-label">Text:</div>
						<textarea data-ng-model="current.text" id="edit-content-text-input"></textarea>
					</div>

					<div id="edit-content-id">{{ current['@id'] }}</div>
					<div id="edit-content-buttons">
						<button id="edit-content-add-button" data-ng-click="save(contentForm)"
								data-ng-disabled="contentForm.$invalid || saving">Save</button>
					</div>
			</div>
			<div id="empty-content" data-ng-click="add(authorForm)" data-ng-show="authorForm.$valid">
				New content goes here...
			</div>
		</form>
		<div class="content" data-ng-repeat="item in content">
			<div class="content-title">{{ item.title }}</div>
			<div class="content-text">{{ item.text }}</div>
			<div class="content-author">{{ item.author }}</div>
			<div class="content-created">{{ item['@created'] | date:'mediumDate' }}</div>
			<div class="content-id">
				<a href="" data-ng-click="edit(item)">{{ item['@id'] }}</a>
			</div>
		</div>
	</body>
</html>