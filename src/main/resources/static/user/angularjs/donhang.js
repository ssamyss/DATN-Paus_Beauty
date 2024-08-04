const app = angular.module("app", []);
app.controller("donhang-ctrl", function($scope, $http, $window) {
	$scope.form = {};
	$scope.donhang = [];
	$scope.donhangchitiet = [];
	$scope.donhangnho = [];
	$scope.taikhoan = [];
	$scope.giohang = [];
	$scope.tongtien = 0;
	$scope.dem = 0;
	$scope.pvc = 0;
	$scope.maDH = 0;
	$scope.maDHCT = 0;

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
		const sdt = document.getElementById("sdt").value;
		const diachi = document.getElementById("diachi").value;
		$http.get('/rest/donhang').then(resp => {
			$scope.donhang = resp.data;
			if ($scope.donhang.length > 0) {
				$scope.maDH = $scope.donhang.length + 1;
			} else {
				$scope.maDH = 1;
			}
			$scope.form.maDH = $scope.maDH;
			$scope.form.tongGia = $scope.tongtien;
			$scope.form.sdt = sdt;
			$scope.form.diaChi = diachi;
			$scope.form.trangThai = 1;
			$scope.form.taiKhoan = $scope.taikhoan;

			var donhang = angular.copy($scope.form);
			$http.post('/rest/donhang', donhang).then(resp => {
				$scope.donhang.push(resp.data);
				$scope.reset();

				$http.get('/rest/donhang/' + $scope.maDH).then(resp => {
					$scope.giohangnho = resp.data;
					for (i = 0; i < $scope.giohang.length; i++) {

						$http.get('/rest/donhangchitiet').then(resp => {
							$scope.donhangchitiet = resp.data;
							if ($scope.donhangchitiet.length > 0) {
								$scope.maDHCT = $scope.donhangchitiet.length + 1;
							} else {
								$scope.maDHCT = 1;
							}
							$scope.form.maDHCT = $scope.maDHCT;
							$scope.form.tongGia = $scope.tongtien;
							$scope.form.sdt = sdt;
							$scope.form.diaChi = diachi;
							$scope.form.trangThai = 1;
							$scope.form.taiKhoan = $scope.taikhoan;

							var donhangchitiet = angular.copy($scope.form);
							$http.post('/rest/donhangchitiet', donhangchitiet).then(resp => {
								
								$scope.reset();

								var item = angular.copy($scope.giohang[i]);
								$http.delete('/rest/giohang/' + item.maGH, item).then(resp => {
									var index = $scope.items.findIndex(p => p.maGH == item.maGH);
									$scope.items.splice(index, 1);
								}).catch(error => {
									alert("Lỗi xóa giỏ hàng dữ liệu!");
								});
							}).catch(error => {
								alert("Lỗi khi thêm đơn hàng chi tiết!");
							});
						}).catch(error => {
							alert("Lỗi khi tải đơn hàng chi tiết!");
						});
					}
					$window.location.href = "/hoa-don/" + $scope.giohangnho.maDH;
				}).catch(error => {
					alert("Lỗi khi tải giỏ hàng!");
				});
			}).catch(error => {
				alert("Lỗi thêm mới đơn hàng!");
			});
		}).catch(error => {
			alert("Lỗi khi tải đơn hàng!");
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
	};

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

		} else if ($scope.selectedValue == 2) {
			$window.location.href = '/vn-pay';
		}
	};

	$scope.initialize();
	$scope.reset();
});