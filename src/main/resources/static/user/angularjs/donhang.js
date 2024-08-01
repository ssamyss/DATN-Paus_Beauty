const app = angular.module("app", []);
app.controller("donhang-ctrl", function($scope, $http, $window) {
	$scope.form = {};
	$scope.donhang = [];
	$scope.taikhoan = [];
	$scope.giohang = [];
	$scope.tongtien = 0;
	$scope.dem = 0;
	$scope.pvc = 0;

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

	$scope.create = function() {
		$http.get('/rest/donhang').then(resp => {
			$scope.donhang = resp.data;
			$scope.form.maDH = $scope.donhang.length;
		}).catch(error => {
			alert("Lỗi khi tải đơn hàng!");
		});
		var item = angular.copy($scope.form);
		$http.post('/rest/donhang', item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			alert("Thêm mới đơn hàng thành công!");
		}).catch(error => {
			alert("Lỗi thêm mới đơn hàng!");
		});
	};

	$scope.vanchuyen = function(item) {
		$scope.tong();
		if (item == 1) {
			$scope.pvc = 20000;
			$scope.tongtien = $scope.tongtien + 20000;
		} else {
			$scope.pvc = 40000;
			$scope.tongtien = $scope.tongtien + 40000;
		}
	}

	$scope.goTo = function() {
		// Lấy tất cả các element có name là "myRadio" (ví dụ)
		const radios = document.getElementsByName("pttt");
		$scope.selectedValue = 0;

		// Duyệt qua từng element
		for (let i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				// Nếu element được chọn, lấy giá trị và thực hiện các tác vụ khác
				$scope.selectedValue = radios[i].value;
				break; // Thoát vòng lặp khi tìm thấy giá trị
			}
		}
		if ($scope.selectedValue == 1) {
			$scope.create();
			$window.location.href = '/hoa-don';
		} else {
			$window.location.href = '/vn-pay';
		}
	};

	$scope.initialize();
	$scope.reset();
});