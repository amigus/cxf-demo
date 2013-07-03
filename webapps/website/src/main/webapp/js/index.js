angular.module('content-ui', ['ngResource']);


function ContentController ($scope, $http, $resource, $window) {
	var content = $resource('http://localhost\\:8081/webservices/content/:id');
	$scope.content = [];

	$window.signinCallback = function(authResult) {
		if (authResult['access_token']) {
			// Use JQuery.ajax instead of $http because the latter doesn't work.
			$.ajax({
			   url: 'https://www.googleapis.com/plus/v1/people/me',
			   headers: { 'Authorization':
					authResult['token_type'] + ' ' + authResult['access_token']
			   }
			}).done(function (response) {
				$scope.author = response;
				$scope.$apply();
			});
			/*
			$http({ method: 'GET', url: 'https://www.googleapis.com/plus/v1/people/me'},
					{ headers: {
						'Authorization': authResult['token_type'] + ' ' +
								authResult['access_token']
					}
			}).success(function(data) {
				$scope.author = data;
				$scope.$apply();
			});
			*/
		}
	};

	content.get(function(response) {
		if (response.id != null) {
			for (var i = 0; i < response.id.length; i++) {
				$scope.content.push(content.get({id: response.id[i]}));
			}
		}
	});

	$scope.add = function(authorForm) {
		if (authorForm.$valid) {
			var current = new content();

			current.author = $scope.author.displayName;
			$scope.current = current;
			$( "#content-editor" ).dialog( "open" );
		}
	};

	$scope.edit = function(item) {
		$scope.current = item;
		$( "#content-editor" ).dialog( "open" );
	};

	$scope.save = function(contentForm) {
		if (contentForm.$valid) {
			var current = $scope.current;

			$scope.saving = true;
			if (current['@id'] == null) {
				$scope.content.unshift(current);
			} else {
				delete current['@id'];
				delete current['@created'];
			}
			current.$save({}, function() {
				$scope.saving = null;
			}, function() {
				$scope.saving = null;
			});
		}
	};
}

$(document).ready(function() {
	$( "#content-editor" ).dialog({ autoOpen: false, modal: true  });
});