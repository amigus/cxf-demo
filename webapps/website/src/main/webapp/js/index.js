angular.module('content-ui', ['ngResource']);


function ContentController ($scope, $http, $resource) {
	var content = $resource('http://localhost\\:8081/webservices/content/:id');

	$scope.content = [];

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

			current.author = $scope.author;
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