const app = angular.module("app", []);
app.controller("tracuudonhang-ctrl", function($scope, $http, $window) {
	$scope.form = {};
	$scope.donhang = [];
	$scope.donhangchitiet = [];
	$scope.donhangnho = [];
	$scope.taikhoan = [];
	$scope.giohang = [];
	$scope.taikhoantimkiem = [];
	$scope.tongtien = 0;
	$scope.dem = 0;
	$scope.pvc = 0;
	$scope.maDH = '';
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

	$scope.tracuu = function() {
		$http.get('/rest/donhang/' + $scope.maDH).then(resp => {
			$scope.donhang = resp.data;
			$http.get('/rest/donhangchitiet/bydh/' + $scope.maDH).then(resp => {
				$scope.donhangchitiet = resp.data;
				xuatDuLieu($scope.donhang);
			}).catch(error => {
				console.log("Lỗi đơn hàng chi tiết: " + error);
			});
		}).catch(error => {
			Swal.fire({
				icon: 'error',
				title: 'Đơn hàng không tồn tại!',
				confirmButtonText: 'OK',
				confirmButtonColor: '#28a745'
			}).then(result => {
				
			});
		});
	};

	async function xuatDuLieu(item) {

		$scope.taikhoantimkiem = await item.taiKhoan;
		anSDT();

		console.log(item);
		console.log($scope.donhangchitiet);
		$scope.initialize();
		$scope.reset();
	}


	async function anSDT() {
		const sdt3socuoi = await $scope.taikhoantimkiem.sdt.slice(-3);
		const laycuoiemail = await $scope.taikhoantimkiem.email.split('@');

		// Tạo chuỗi mới với các chữ số đầu được thay thế bằng 'x'
		const sdtDaAn = await 'x'.repeat($scope.taikhoantimkiem.sdt.length - 3) + sdt3socuoi;

		// Lấy 2 ký tự đầu
		const haiKyTuDau = await laycuoiemail[0].substring(0, 2);
		// Lấy 2 ký tự cuối
		const haiKyTuCuoi = await laycuoiemail[0].slice(-2);
		// Chuyển ký tự ở giữa thành x
		const emailDaAn = await 'x'.repeat(laycuoiemail[0].length - 4);

		$scope.sdt = await sdtDaAn;
		$scope.email = await haiKyTuDau + emailDaAn + haiKyTuCuoi + '@' + laycuoiemail[1];
	};

	$scope.tong = function() {
		$scope.tongtien = 0;
		for (let i = 0; i < $scope.dem; i++) {
			$scope.tongtien = $scope.giohang[i].sanPham.gia * $scope.giohang[i].soLuong + $scope.tongtien;
		}
	};

	$scope.initialize();
	$scope.reset();
});