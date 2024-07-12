const app = angular.module("app", []);
app.controller("giohang-ctrl", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.giohang = [];
	$scope.dem = 0;

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		//Load tài khoản đăng nhập
		$http.get("rest/taikhoan/tentaikhoan").then(resp => {
			$scope.tentaikhoan = resp.data;
			//Load số lượng sản phẩm trong giỏ hàng
			$http.get('/rest/giohang/byttk/' + $scope.tentaikhoan.tenTaiKhoan).then(resp => {
				$scope.giohang = resp.data;
				$scope.dem = $scope.giohang.length;
			}).catch(error => {
				alert("Lỗi khi tải giỏ hàng!");
			});
		}).catch(error => {
			console.error("Lỗi khi tải tên tài khoản :", error);
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
			item.soLuong = item.soLuong - 1;
	        $scope.items[index] = angular.copy(item);
		}).catch(error => {
			alert("Lỗi giảm số lượng!");
			console.log("Error", error);
		});
	};

	$scope.cong = function(item) {
		$http.put('/rest/giohang/' + item.maGH, item).then(resp => {
			item.soLuong = item.soLuong + 1;
		}).catch(error => {
			alert("Lỗi giảm số lượng!");
			console.log("Error", error);
		});
	};

	$scope.initialize();
	$scope.reset();
});
