const app = angular.module("app", []);
app.controller("donhang-ctrl", function($scope, $http) {
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
		$http.get("/rest/donhang/dangxuly").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
			console.log("Đơn hàng:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng:", error);
		});
	};
	
	//xóa tất cả đơn hàng
	$scope.deleteAll = function() {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn xóa tất cả đơn hàng không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				
				$http.delete('/rest/donhang').then(resp => {
					$scope.items = []; 
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Đơn hàng đã được xóa thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
					$scope.initialize(); 
				}).catch(error => {
					alert("Lỗi khi xóa tất cả đơn hàng!");
					console.log("Error", error);
				});
			}
		});
	};
	
	//xóa sản phẩm theo mã đơn hàng
	$scope.delete = function(item) {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn xóa đơn hàng này không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				
				$http.delete('/rest/donhang/' + item.maDH, item).then(resp => {
					var index = $scope.items.findIndex(p => p.maDH == item.maDH);
					$scope.items.splice(index, 1);
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Đơn hàng đã được xóa thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
					$scope.reset();
					$scope.initialize();
				}).catch(error => {
					alert("Lỗi xóa đơn hàng!");
					console.log("Error", error);
				});
			}
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
	
	//phân trang tất đơn hàng tất cả trạng thái		
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
