const app = angular.module("app", []);
app.controller("thongtincanhan-ctrl", function($scope, $http) {
	$scope.form = {};
	$scope.taikhoan = [];
	$scope.giohang = [];
	$scope.tongtien = 0;
	$scope.dem = 0;

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		//Load tài khoản đăng nhập
		$http.get("rest/taikhoan/tentaikhoan").then(resp => {
			$scope.taikhoan = resp.data;
			//Load số lượng sản phẩm trong giỏ hàng
			$http.get('/rest/giohang/byttk/' + $scope.taikhoan.tenTaiKhoan).then(resp => {
				$scope.giohang = resp.data;
				$scope.dem = $scope.giohang.length;
			}).catch(error => {
				alert("Lỗi khi tải giỏ hàng!");
			});
		}).catch(error => {
			console.error("Lỗi khi tải tên tài khoản :", error);
		});
	};

	$scope.initialize();
	$scope.reset();
});