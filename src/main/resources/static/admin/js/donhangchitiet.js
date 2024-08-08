const app = angular.module("app", []);
app.controller("donhangchitiet-ctrl", function($scope, $http, $location) {
	$scope.form = {
		status: 'Processsing',
	};;
	$scope.accounts = [];
	$scope.dhchitiets = [];
	$scope.sanpham = [];
	$scope.donhang = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		// Load đơn hàng chi tiết
		$http.get("/rest/donhangchitiet/bymadh/" + $location.absUrl().split("/")[4]).then(resp => {
			$scope.dhchitiets = resp.data;
			$scope.dhchitiets.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
			console.log("Đơn hàng chi tiết:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng chi tiết:", error);
		});

		// Load đơn hàng thông tin
		$http.get("/rest/donhang/" + $location.absUrl().split("/")[4]).then(resp => {
			$scope.donhang = resp.data;
			$scope.form = {
				maDH: $scope.donhang.maDH,
				taiKhoan: $scope.donhang.taiKhoan,
				diaChi: $scope.donhang.diaChi,
				sdt: $scope.donhang.sdt,
				tongGia: $scope.donhang.tongGia,
				createDate: new Date($scope.donhang.createDate),
				trangThai: $scope.getStatusText($scope.donhang.trangThai),
			};
			console.log("Đơn hàng thông tin:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng thông tin:", error);
		});
	};

	$scope.getStatusText = function(status) {
		switch (status) {
			case 'DANG_XU_LY':
				return 'Đang Xử Lý';
			case 'HOAN_TAT':
				return 'Hoàn Thành';
			case 'HUY_DON':
				return 'Đã Hủy';
			default:
				return 'Chưa rõ';
		}
	};

	$scope.initialize();
	/*$scope.reset();*/
});

