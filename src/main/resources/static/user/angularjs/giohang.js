const app = angular.module("app", []);
app.controller("giohang-ctrl", function($scope, $http, $location) {
	$scope.form = {};
	$scope.items = [];
	$scope.giohang = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		$http.get('/rest/giohang/byttk/' + $location.absUrl().split("/")[4]).then(resp => {
			$scope.giohang = resp.data;
			$scope.giohang.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			alert("Lỗi khi tải giỏ hàng!");
		});
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

	$scope.tru = function(item) {
		$http.put('/rest/giohang/' + item.maGH, item).then(resp => {
			var index = $scope.giohang.findIndex(p => p.maGH == item.maGH);
			$scope.giohang[index].soLuong = item.soLuong - 1;
		}).catch(error => {
			alert("Lỗi giảm số lượng!");
			console.log("Error", error);
		});
	};

	$scope.cong = function(item) {
		$http.put('/rest/giohang/' + item.maGH, item).then(resp => {
			var index = $scope.giohang.findIndex(p => p.maGH == item.maGH);
			$scope.giohang[index].soLuong = item.soLuong + 1;
		}).catch(error => {
			alert("Lỗi giảm số lượng!");
			console.log("Error", error);
		});
	};

	$scope.initialize();
	$scope.reset();
});
