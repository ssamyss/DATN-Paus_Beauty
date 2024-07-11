const app = angular.module("app", []);
app.controller("giohang-ctrl", function($scope, $http) {
    $scope.form = {};
    $scope.items = [];
	$scope.sanPham = [];

    $scope.reset = function() {
        $scope.form = {
            createDate: new Date(),
        };
    };
			
	$scope.create = function(item) {
		$scope.form = angular.copy(item);
	    var item = angular.copy($scope.form);
	    $http.post('/rest/giohang', item).then(resp => {
	        $scope.items.push(resp.data);
	        $scope.reset();
	        alert("Thêm mới thương hiệu thành công");
	    }).catch(error => {
	        alert("Lỗi thêm mới!");
	        console.log("Error", error);
	    });
	};

    $scope.reset();
	
});
