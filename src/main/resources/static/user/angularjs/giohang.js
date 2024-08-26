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
				$scope.updateCartIcon();
			}).catch(error => {
				alert("Lỗi khi tải giỏ hàng!");
			});
		}).catch(error => {
			console.error("Lỗi khi tải tên tài khoản :", error);
		});
	};

	$scope.tong = function() {
		$scope.tongtien = 0;
		var i = 0;
		for (i; i < $scope.dem; i++) {
			$scope.tongtien = $scope.giohang[i].sanPham.gia * $scope.giohang[i].soLuong;
		}
	};

	$scope.updateCartIcon = function() {
		$scope.dem = $scope.giohang.reduce((acc, item) => acc + item.soLuong, 0);
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
		var sanpham = angular.copy(item);
		sanpham.soLuong = sanpham.soLuong - 1;
		if (sanpham.soLuong < 1) {
			Swal.fire({
				icon: 'error',
				title: 'Giảm số lượng thất bại',
				text: 'Số lượng đã đạt giới hạn!',
				confirmButtonText: 'OK',
				confirmButtonColor: '#28a745'
			});
			return;
		}
		$scope.congtruGH(sanpham);
	};

	$scope.cong = function(item) {
		var sanpham = angular.copy(item);
		sanpham.soLuong = sanpham.soLuong + 1;
		$scope.congtruGH(sanpham);
	};

	$scope.congtruGH = function(item) {
		$http.put('/rest/giohang/' + item.maGH, item).then(resp => {
			var index = $scope.giohang.findIndex(p => p.maGH == item.maGH);
			$scope.giohang[index] = angular.copy(item);
			$scope.tong();
		}).catch(error => {
			console.log(error);
		});
	}

	$scope.xoa = function(item) {
		Swal.fire({
			title: 'Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete('/rest/giohang/' + item.maGH).then(resp => {
					// Xóa mục khỏi giỏ hàng trong frontend
					var index = $scope.giohang.findIndex(p => p.maGH == item.maGH);
					$scope.initialize();
					$scope.reset();
					Swal.fire('Xóa thành công!', '', 'success');
				}).catch(error => {
					Swal.fire('Lỗi khi xóa!', 'Có lỗi xảy ra, vui lòng thử lại.', 'error');
				});
			}
		});
	};

	$scope.goToPage = function() {
		$window.location.href = '/don-hang';
	};

	$scope.initialize();
	$scope.reset();
});