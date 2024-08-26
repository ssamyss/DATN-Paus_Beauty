const app = angular.module("app", []);
app.controller("donhangchitiet-ctrl", function($scope, $http, $window ,$location) {
	$scope.form = {};
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
				note: $scope.donhang.note,
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
			case 'CHO_LAY_HANG':
				return 'Chờ Lấy Hàng';
			case 'DANG_GIAO_HANG':
				return 'Đang Giao Hàng';
			case 'HOAN_TAT':
				return 'Hoàn Thành';
			case 'HUY_DON':
				return 'Đã Hủy';
			default:
				return 'Chưa rõ';
		}
	};


	$scope.update = function() {
	    Swal.fire({
	        title: 'Xác nhận',
	        text: "Bạn có chắc chắn muốn cập nhật đơn hàng không?",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        confirmButtonText: 'Cập nhật',
	        cancelButtonText: 'Hủy'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            // Prepare form data for update
	            $scope.form.trangThai = "CHO_LAY_HANG";
	            $scope.form.createDate = new Date();

	            var item = angular.copy($scope.form);

	            // Send HTTP PUT request to update the order
	            $http.put('/rest/donhang/' + item.maDH, item).then(resp => {
	                if (Array.isArray($scope.donhang)) {
	                    var index = $scope.donhang.findIndex(p => p.maDH == item.maDH);
	                    if (index !== -1) {
	                        $scope.donhang[index] = angular.copy(item);
	                    }
	                }
	                // Show success message
	                Swal.fire({
	                    icon: 'success',
	                    title: 'Thành công',
	                    text: 'Cập nhật đơn hàng thành công!',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#28a745'
	                }).then(() => {
	                    // Redirect to new page
	                    $window.location.href = '/dataOrder/cholayhang';
	                });
	            }).catch(error => {
	                Swal.fire({
	                    icon: 'error',
	                    title: 'Lỗi',
	                    text: 'Có lỗi xảy ra khi cập nhật.',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#d33'
	                });
	                console.log("Error", error);
	            });
	        }
	    });
	};
	
	$scope.updateCLH = function() {
	    Swal.fire({
	        title: 'Xác nhận',
	        text: "Bạn có chắc chắn muốn cập nhật đơn hàng không?",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        confirmButtonText: 'Cập nhật',
	        cancelButtonText: 'Hủy'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            // Prepare form data for update
	            $scope.form.trangThai = "DANG_GIAO_HANG";
	            $scope.form.createDate = new Date();

	            var item = angular.copy($scope.form);

	            // Send HTTP PUT request to update the order
	            $http.put('/rest/donhang/' + item.maDH, item).then(resp => {
	                if (Array.isArray($scope.donhang)) {
	                    var index = $scope.donhang.findIndex(p => p.maDH == item.maDH);
	                    if (index !== -1) {
	                        $scope.donhang[index] = angular.copy(item);
	                    }
	                }
	                // Show success message
	                Swal.fire({
	                    icon: 'success',
	                    title: 'Thành công',
	                    text: 'Cập nhật đơn hàng thành công!',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#28a745'
	                }).then(() => {
	                    // Redirect to new page
	                    $window.location.href = '/dataOrder/danggiaohang';
	                });
	            }).catch(error => {
	                Swal.fire({
	                    icon: 'error',
	                    title: 'Lỗi',
	                    text: 'Có lỗi xảy ra khi cập nhật.',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#d33'
	                });
	                console.log("Error", error);
	            });
	        }
	    });
	};
	
	$scope.updateDGH = function() {
	    Swal.fire({
	        title: 'Xác nhận',
	        text: "Bạn có chắc chắn muốn cập nhật đơn hàng không?",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        confirmButtonText: 'Cập nhật',
	        cancelButtonText: 'Hủy'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            // Prepare form data for update
	            $scope.form.trangThai = "HOAN_TAT";
	            $scope.form.createDate = new Date();

	            var item = angular.copy($scope.form);

	            // Send HTTP PUT request to update the order
	            $http.put('/rest/donhang/' + item.maDH, item).then(resp => {
	                if (Array.isArray($scope.donhang)) {
	                    var index = $scope.donhang.findIndex(p => p.maDH == item.maDH);
	                    if (index !== -1) {
	                        $scope.donhang[index] = angular.copy(item);
	                    }
	                }
	                // Show success message
	                Swal.fire({
	                    icon: 'success',
	                    title: 'Thành công',
	                    text: 'Cập nhật đơn hàng thành công!',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#28a745'
	                }).then(() => {
	                    // Redirect to new page
	                    $window.location.href = '/dataOrder/hoanthanh';
	                });
	            }).catch(error => {
	                Swal.fire({
	                    icon: 'error',
	                    title: 'Lỗi',
	                    text: 'Có lỗi xảy ra khi cập nhật.',
	                    confirmButtonText: 'OK',
	                    confirmButtonColor: '#d33'
	                });
	                console.log("Error", error);
	            });
	        }
	    });
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
						$window.location.href = '/dataOrder/huydon';
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
	/*$scope.reset();*/
});

