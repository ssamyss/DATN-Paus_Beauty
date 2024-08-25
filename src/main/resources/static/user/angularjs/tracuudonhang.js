const app = angular.module("app", []);
app.controller("tracuudonhang-ctrl", function($scope, $http, $window) {
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
	
	$scope.tracuu = function() {
		//const pttc = document.getElementsByName(pttc).value;
		console.log(document.getElementsByName(maDH).value);
		//console.log(pttc);
	}

	$scope.timbangma = function(item) {
		$http.get('/rest/donhang/' + item).then(resp => {
			$scope.donhang = resp.data;
		}).catch(error => {
			alert("Lỗi khi tải đơn hàng!");
		});
	};
	
	$scope.timbangemail = function(item) {
		$http.get('/rest/donhang/' + item).then(resp => {
			$scope.donhang = resp.data;
		}).catch(error => {
			alert("Lỗi khi tải đơn hàng!");
		});
	};

	async function anSDT() {
		const sdt3socuoi = await $scope.taikhoan.sdt.slice(-3);
		const laycuoiemail = await $scope.taikhoan.email.split('@');

		// Tạo chuỗi mới với các chữ số đầu được thay thế bằng 'x'
		const sdtDaAn = await 'x'.repeat($scope.taikhoan.sdt.length - 3) + sdt3socuoi;

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