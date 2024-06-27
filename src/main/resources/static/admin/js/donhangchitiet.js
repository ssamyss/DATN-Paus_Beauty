const app = angular.module("app", []);
app.controller("donhangchitiet-ctrl", function($scope, $http, $location) {
	$scope.form = {
		status: 'Processsing',
	};;
	$scope.accounts = [];
	$scope.dhchitiets = [];
	$scope.sanpham = [];
	$scope.donhang = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};
	
	$scope.initialize = function() {
		// Load đơn hàng chi tiết
		$http.get("/rest/donhangchitiet/bymadh/" + $location.absUrl().split("/")[4]).then(resp => {
			$scope.dhchitiets = resp.data;
			$scope.dhchitiets.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
			console.log("Đơn hàng chi tiết:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng chi tiết:", error);
		});
	};

	$scope.initialize();
	/*$scope.reset();*/
});

