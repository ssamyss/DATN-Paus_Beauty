const app = angular.module("app", []);
app.controller("donhangDGH-ctrl", function($scope, $http) {
	$scope.form = {
		status: 'Processsing',
	};;
	$scope.accounts = [];
	$scope.items = [];

	$scope.dhdangxuly = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		// Load đơn hàng
		$http.get("/rest/donhang/danggiaohang").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
			console.log("Đơn hàng:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng:", error);
		});
	};


	$scope.getStatusText = function(status) {
		switch (status) {
			case 'DANG_XU_LY':
				return 'Đang xử lý';
			case 'CHO_LAY_HANG':
				return 'Chờ lấy hàng';
			case 'DANG_GIAO_HANG':
				return 'Đang giao hàng';
			case 'HOAN_TAT':
				return 'Hoàn thành';
			case 'HUY_DON':
				return 'Đã hủy';
			default:
				return 'Chưa rõ';
		}
	};


	$scope.pager = {
		page: 0,
		size: 5,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}

	}

	$scope.initialize();
	/*$scope.reset();*/
});
