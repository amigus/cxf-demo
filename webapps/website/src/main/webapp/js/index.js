angular.module('content-ui', ['ngResource']);


function ContentController ($scope, $http, $resource) {
	var content = $resource('http://localhost\\:8081/webservices/content/:id');

	$scope.content = [];

	content.get(function(response) {
		var ids = response.id;

		if (ids != null) {
			for (var i = 0; i < ids.length; i++) {
				content.get({id: ids[i]}, function(item) {
					$scope.content.push(item);
				});
			}
		}
	});

	$scope.add = function(authorForm) {
		$scope.id = $scope.title = $scope.text = null;

		if (authorForm.$valid) {
			$( "#content-editor" ).dialog( "open" );
		}
	};

	$scope.edit = function(item) {
		$scope.author = item.author;
		$scope.title = item.title;
		$scope.text = item.text;
		$scope.id = item['@id'];
		$( "#content-editor" ).dialog( "open" );
	};

	$scope.save = function(contentForm) {
		var newcontent = new content();

		if (contentForm.$valid) {
			newcontent.author = $scope.author;
			newcontent.title = $scope.title;
			newcontent.text = $scope.text;
			$scope.saving = true;

			newcontent.$save(function() {
				if ($scope.id != null) {
					content.remove({ id: $scope.id });
					for (var i = 0; i < $scope.content.length; i++) {
						if ($scope.content[i]['@id'] == $scope.id) {
							$scope.content.splice(i, 1, newcontent);
						}
					}
				} else {
					$scope.content.push(newcontent);
				}

				$scope.id = newcontent['@id'];
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