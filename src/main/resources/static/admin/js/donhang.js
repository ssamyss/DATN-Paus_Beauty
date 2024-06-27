const app = angular.module("app", []);
app.controller("donhang-ctrl", function($scope, $http) {
	$scope.form = {
		status: 'Processsing',
	};;
	$scope.accounts = [];
	$scope.items = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		// Load đơn hàng
		$http.get("/rest/donhang").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
			console.log("Đơn hàng:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng:", error);
		});
	};

	$scope.initialize();
	/*$scope.reset();*/
});
