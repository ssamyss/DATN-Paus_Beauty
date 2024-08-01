const app = angular.module("app", []);
app.controller("giohang-ctrl", function($scope, $http, $window) {
	$scope.form = {};
	$scope.items = [];
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
			$scope.tentaikhoan = resp.data;
			//Load số lượng sản phẩm trong giỏ hàng
			$http.get('/rest/giohang/byttk/' + $scope.tentaikhoan.tenTaiKhoan).then(resp => {
				$scope.giohang = resp.data;
				$scope.dem = $scope.giohang.length;
				$scope.tong();
			}).catch(error => {
				alert("Lỗi khi tải giỏ hàng!");
			});
		}).catch(error => {
			console.error("Lỗi khi tải tên tài khoản :", error);
		});
	};

	$scope.tong = function() {
		$scope.tongtien = 0;
		for (let i = 0; i < $scope.dem; i++) {
			$scope.tongtien = $scope.giohang[i].sanPham.gia * $scope.giohang[i].soLuong + $scope.tongtien;
		}
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
		});
	};

	$scope.tru = function(item) {
		$http.put('/rest/giohang/' + item.maGH, item).then(resp => {
			var index = $scope.giohang.findIndex(p => p.maGH == item.maGH);
			$scope.giohang[index].soLuong = $scope.giohang[index].soLuong - 1;
		}).catch(error => {
			alert("Lỗi giảm số lượng!");
		});
		$scope.tong();
	};

	$scope.cong = function(item) {
		$http.put('/rest/giohang/' + item.maGH, item).then(resp => {
			var index = $scope.giohang.findIndex(p => p.maGH == item.maGH);
			$scope.giohang[index].soLuong = $scope.giohang[index].soLuong + 1;
		}).catch(error => {
			alert("Lỗi giảm số lượng!");
		});
		$scope.tong();
	};
	
	$scope.goToPage = function() {
        $window.location.href = '/don-hang';
    };

	$scope.initialize();
	$scope.reset();
});