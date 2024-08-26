const app = angular.module("app", []);
app.controller("quanlydonhang-ctrl", function($scope, $http) {
	$scope.accounts = [];
	$scope.donhang = [];
	$scope.dhchitiets = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		// Load đơn hàng
		$http.get('/rest/donhang/canhan').then(resp => {
			$scope.donhang = resp.data;
			$scope.donhang.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng:", error);
		});
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
	
	$scope.prepareHuyDon = function(item) {
	    $scope.form = {
	        maDH: item.maDH,
	        note: '', // Initialize note field
			hoVaTen: item.taiKhoan.hoVaTen,
			sdt: item.sdt,
			diaChi: item.diaChi,
			tongGia: item.tongGia,
	    };
	};

	$scope.huydon = function() {
	    if (!$scope.form.note || $scope.form.note.trim() === "") {
	        Swal.fire({
	            icon: 'warning',
	            title: 'Lỗi',
	            text: 'Vui lòng nhập lý do hủy đơn.',
	            confirmButtonText: 'OK',
	            confirmButtonColor: '#d33'
	        });
	        return;
	    }

	    Swal.fire({
	        title: 'Xác nhận',
	        text: "Bạn có chắc chắn muốn hủy đơn hàng này không?",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        confirmButtonText: 'Cập nhật',
	        cancelButtonText: 'Hủy'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            $scope.form.trangThai = "HUY_DON";
	            $scope.form.createDate = new Date();
	            $scope.form.note = $scope.form.note.trim();

	            var item = angular.copy($scope.form);
	            $http.put('/rest/donhang/' + item.maDH, item).then(resp => {
	                if (Array.isArray($scope.donhang)) {
	                    var index = $scope.donhang.findIndex(p => p.maDH == item.maDH);
	                    if (index !== -1) {
	                        $scope.donhang[index] = angular.copy(item);
	                    }
	                }
	                Swal.fire({
	                    icon: 'success',
	                    title: 'Thành công',
	                    text: 'Hủy đơn hàng thành công!',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#28a745'
	                }).then(() => {
	                    $('#cancelOrderModal').modal('hide');
	                });
					
	            }).catch(error => {
	                Swal.fire({
	                    icon: 'error',
	                    title: 'Lỗi',
	                    text: 'Có lỗi xảy ra khi hủy đơn hàng.',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#d33'
	                });
	                console.log("Error", error);
	            });
	        }
	    });
	};

	$scope.initialize();
	$scope.reset();
}

);

