const app = angular.module("app", []);
app.controller("danhmuc-ctrl", function($scope, $http) {
	$scope.form = {};
	$scope.dmucsp = [];
	$scope.cates = [];
	$scope.bras = [];
	$scope.items = [];

	$scope.reset = function() {
		$scope.form = {};
		$scope.imageURL = ''; // Đặt lại đường dẫn ảnh về trống để không hiển thị ảnh
	};


	$scope.initialize = function() {
		// Load danh mục sản phẩm
		$http.get("/rest/danhmucsanpham").then(resp => {
			$scope.dmucsp = resp.data;
			console.log("Danh mục sản phẩm:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải danh mục sản phẩm:", error);
		});

		// Load loại sản phẩm
		$http.get("/rest/loaisanpham").then(resp => {
			$scope.cates = resp.data;
			console.log("Loại sản phẩm:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải loại sản phẩm:", error);
		});

		// Load thương hiệu
		$http.get("/rest/thuonghieu").then(resp => {
			$scope.bras = resp.data;
			console.log("Thương hiệu:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải thương hiệu:", error);
		});
	};
	
	
	$scope.createDanhMuc = function() {
	    if ($scope.danhMucForm.$valid) {
	        var item = angular.copy($scope.form);
	        $http.post('/rest/danhmucsanpham', item).then(resp => {
	            $scope.items.push(resp.data);
	            Swal.fire({
	                icon: 'success',
	                title: 'Thành công',
	                text: 'Thêm mới danh mục thành công!',
	                confirmButtonText: 'OK',
	                confirmButtonColor: '#28a745'
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    // Hide the modal
	                    $('#addDanhMucModal').modal('hide');
	                }
	            });
	            $scope.reset();
	            $scope.initialize();
	        }).catch(error => {
	            alert("Lỗi thêm mới!");
	            console.log("Error", error);
	        });
	    } else {
	        // Nếu form không hợp lệ, thông báo lỗi
	        Swal.fire({
	            icon: 'error',
	            title: 'Lỗi',
	            text: 'Vui lòng điền đầy đủ thông tin!',
	            confirmButtonText: 'OK',
	            confirmButtonColor: '#d33'
	        });
	    }
	};

	//Thêm loại sản phẩm
	$scope.createLoaiSP = function() {
	    if ($scope.productCategoryForm.$valid) {
	        var item = angular.copy($scope.form);
	        $http.post('/rest/loaisanpham', item).then(resp => {
	            $scope.items.push(resp.data);
	            Swal.fire({
	                icon: 'success',
	                title: 'Thành công',
	                text: 'Thêm loại sản phẩm thành công!',
	                confirmButtonText: 'OK',
	                confirmButtonColor: '#28a745'
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    // Hide the modal
	                    $('#addProductCategoryModal').modal('hide');
	                }
	            });
	            $scope.reset();
	            $scope.initialize();
	        }).catch(error => {
	            alert("Lỗi thêm mới!");
	            console.log("Error", error);
	        });
	    } else {
	        // Nếu form không hợp lệ, thông báo lỗi
	        Swal.fire({
	            icon: 'error',
	            title: 'Lỗi',
	            text: 'Vui lòng điền đầy đủ thông tin!',
	            confirmButtonText: 'OK',
	            confirmButtonColor: '#d33'
	        });
	    }
	};

	// Thương hiệu
	$scope.createTH = function() {
	    // Check if form and image are valid
	    if ($scope.brandForm.$valid && $scope.form.anhTH) {
	        var item = angular.copy($scope.form);
	        $http.post('/rest/thuonghieu', item).then(resp => {
	            $scope.items.push(resp.data);
	            Swal.fire({
	                icon: 'success',
	                title: 'Thành công',
	                text: 'Thêm thương hiệu thành công!',
	                confirmButtonText: 'OK',
	                confirmButtonColor: '#28a745'
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    $('#addBrandModal').modal('hide');
	                }
	            });
	            $scope.reset();
	            $scope.initialize();
	        }).catch(error => {
	            alert("Lỗi thêm mới!");
	            console.log("Error", error);
	        });
	    } else {
	        Swal.fire({
	            icon: 'error',
	            title: 'Lỗi',
	            text: 'Vui lòng điền đầy đủ thông tin và chọn ảnh!',
	            confirmButtonText: 'OK',
	            confirmButtonColor: '#d33'
	        });
	    }
	};





	$scope.imageChanged = function(files) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$scope.$apply(function() {
				// Hiển thị trước ảnh đã chọn
				$scope.imageURL = e.target.result;
			});
		};
		// Đọc file ảnh
		reader.readAsDataURL(files[0]);

		// Gửi file ảnh lên server
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/thuonghieu', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			if (resp.data.error) {
				alert(resp.data.error);
			} else {
				// Lưu đường dẫn ảnh vào biến form.anhTH
				$scope.form.anhTH = resp.data.name;
			}
		}).catch(error => {
			alert("Lỗi tải ảnh!");
			console.log("Error", error);
		});
	};

	//xóa thương hiệu theo mã thương hiệu
	$scope.deleteTH = function(item) {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn xóa thương hiệu này không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete('/rest/thuonghieu/' + item.maTH, item).then(resp => {
					var index = $scope.items.findIndex(p => p.maTH == item.maTH);
					$scope.items.splice(index, 1);
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Thương hiệu đã được xóa thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
					$scope.reset();
					$scope.initialize();
				}).catch(error => {
					alert("Lỗi xóa dữ liệu!");
					console.log("Error", error);
				});
			}
		});
	};

	//xóa loại sản phẩm theo mã loại sản phẩm
	$scope.deleteLSP = function(item) {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn xóa loại sản phẩm này không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete('/rest/loaisanpham/' + item.maPL, item).then(resp => {
					var index = $scope.items.findIndex(p => p.maPL == item.maPL);
					$scope.items.splice(index, 1);
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Loại sản phẩm đã được xóa thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
					$scope.reset();
					$scope.initialize();
				}).catch(error => {
					alert("Lỗi xóa dữ liệu!");
					console.log("Error", error);
				});
			}
		});
	};


	$scope.pager = {
		page: 0,
		size: 5,
		get cates() {
			var start = this.page * this.size;
			return $scope.cates.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.cates.length / this.size);
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

	// Phân trang cho thương hiệu
	$scope.pagers = {
		page: 0,
		size: 5,
		get bras() {
			var start = this.page * this.size;
			return $scope.bras.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.bras.length / this.size);
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

	// Thêm dữ liệu từ file Excel
	$scope.importDanhMuc = function(files) {
		var reader = new FileReader();
		reader.onloadend = async () => {
			var workbook = new ExcelJS.Workbook();
			await workbook.xlsx.load(reader.result);
			const worksheet = workbook.getWorksheet('Sheet1');
			let successCount = 0; // Counter to keep track of successful imports

			worksheet.eachRow((row, index) => {
				if (index > 1) {
					let danhmucsanpham = {
						maLSP: row.getCell(1).value,
						tenLSP: row.getCell(2).value
					};

					var url = "http://localhost:8080/rest/danhmucsanpham";
					$http.post(url, danhmucsanpham).then(resp => {
						console.log("Success", resp.data);
						$scope.initialize();
						successCount++;
					}).catch(error => {
						console.log("Error", error);
					});
				}
			});
			setTimeout(() => {
				if (successCount > 0) {
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Thêm danh muc thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
				}
			}, 1000);
		};
		reader.readAsArrayBuffer(files[0]);
	};

	// Thêm dữ liệu từ file Excel
	$scope.importLoaiSP = function(files) {
		var reader = new FileReader();
		reader.onloadend = async () => {
			var workbook = new ExcelJS.Workbook();
			await workbook.xlsx.load(reader.result);
			const worksheet = workbook.getWorksheet('Sheet1');
			let successCount = 0; // Counter to keep track of successful imports

			worksheet.eachRow((row, index) => {
				if (index > 1) {
					let loaisanpham = {
						maPL: row.getCell(1).value,
						tenPL: row.getCell(2).value,
						danhMucLoaiSanPham: { maLSP: row.getCell(3).value }
					};

					var url = "http://localhost:8080/rest/loaisanpham";
					$http.post(url, loaisanpham).then(resp => {
						console.log("Success", resp.data);
						$scope.initialize();
						successCount++;
					}).catch(error => {
						console.log("Error", error);
					});
				}
			});
			setTimeout(() => {
				if (successCount > 0) {
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Thêm loai sản phẩm thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
				}
			}, 1000);
		};
		reader.readAsArrayBuffer(files[0]);
	};

	// Thêm dữ liệu từ file Excel
	$scope.importTH = function(files) {
		var reader = new FileReader();
		reader.onloadend = async () => {
			var workbook = new ExcelJS.Workbook();
			await workbook.xlsx.load(reader.result);
			const worksheet = workbook.getWorksheet('Sheet1');
			let successCount = 0; // Counter to keep track of successful imports

			worksheet.eachRow((row, index) => {
				if (index > 1) {
					let thuonghieu = {
						tenTH: row.getCell(2).value,
						anhTH: row.getCell(1).value
					};

					var url = "http://localhost:8080/rest/thuonghieu";
					$http.post(url, thuonghieu).then(resp => {
						console.log("Success", resp.data);
						$scope.initialize();
						successCount++;
					}).catch(error => {
						console.log("Error", error);
					});
				}
			});
			setTimeout(() => {
				if (successCount > 0) {
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Thêm thương hiệu thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
				}
			}, 1000);
		};
		reader.readAsArrayBuffer(files[0]);
	};


	$scope.initialize();
	$scope.reset();

});