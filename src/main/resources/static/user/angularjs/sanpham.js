const app = angular.module("app", []);
app.controller("sanpham-index", function($scope, $http, $location) {
	$scope.form = {};
	$scope.dmucsp = [];
	$scope.cates = [];
	$scope.bras = [];
	$scope.items = [];
	$scope.tentaikhoan = [];
	$scope.giohang = [];
	$scope.dem = 0;
	$scope.sanpham = [];
	$scope.sanphambanchay = [];
	$scope.thuonghieu = [];
	$scope.sanpham2 = [];

	$scope.reset = function() {
		$scope.form = {};
	};

	$scope.initialize = function() {
		// Load sản phẩm
		$http.get("/rest/sanpham").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
		});

		// Load danh mục sản phẩm
		$http.get("/rest/danhmucsanpham").then(resp => {
			$scope.dmucsp = resp.data;
		}).catch(error => {
			console.error("Lỗi khi tải danh mục sản phẩm:", error);
		});

		// Load loại sản phẩm
		$http.get("/rest/loaisanpham").then(resp => {
			$scope.cates = resp.data;
		}).catch(error => {
			console.error("Lỗi khi tải loại sản phẩm:", error);
		});

		// Load thương hiệu
		$http.get("/rest/thuonghieu").then(resp => {
			$scope.bras = resp.data;
		}).catch(error => {
			console.error("Lỗi khi tải thương hiệu:", error);
		});

		// Load tài khoản đăng nhập
		$http.get("rest/taikhoan/tentaikhoan").then(resp => {
			$scope.tentaikhoan = resp.data;
			// Load giỏ hàng
			$http.get('/rest/giohang/byttk/' + $scope.tentaikhoan.tenTaiKhoan).then(resp => {
				$scope.giohang = resp.data;
				$scope.giohang.forEach(item => {
					item.createDate = new Date(item.createDate);
				});
				$scope.dem = $scope.giohang.length;
				$scope.tong();
				$scope.updateCartIcon();
			}).catch(error => {
				alert("Lỗi khi tải giỏ hàng!");
			});
		}).catch(error => {
			console.error(error);
		});


		$http.get("/rest/sanpham/randomProductsByCategory?categoryName=Trang điểm").then(resp => {
			$scope.sanpham = resp.data;
			$scope.sanpham.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
		});
		$http.get("/rest/sanpham/randomProductsByCategory?categoryName=Chăm sóc cơ thể").then(resp => {
			$scope.sanpham2 = resp.data;
			$scope.sanpham2.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
		});

		$http.get("/rest/sanpham/5spbanchay").then(resp => {
			$scope.sanphambanchay = resp.data;
			$scope.sanphambanchay.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
		});

		// Load sản phẩm
		$http.get("/rest/thuonghieu/random").then(resp => {
			$scope.thuonghieu = resp.data;
			$scope.thuonghieu.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
		});

	};

	$scope.tong = function() {
		$scope.tongtien = 0;
		for (let i = 0; i < $scope.dem; i++) {
			$scope.tongtien += $scope.giohang[i].sanPham.gia * $scope.giohang[i].soLuong;
		}
	};

	$scope.updateCartIcon = function() {
		$scope.dem = $scope.giohang.reduce((acc, item) => acc + item.soLuong, 0);
	};

	$scope.openQuickView = function(item) {
		$scope.form = angular.copy(item);
		$('#quickViewModal').modal('show');
	};


	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$('.js-modal1').addClass('show-modal1');
	}

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
		$http.post('/rest/upload/product', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			if (resp.data.error) {
				alert(resp.data.error);
			} else {
				// Lưu đường dẫn ảnh vào biến form.anh
				$scope.form.anh = resp.data.name;
			}
		}).catch(error => {
			alert("Lỗi tải ảnh!");
			console.log("Error", error);
		});
	};

	// Lấy 5 chữ đầu
	$scope.getFirstFiveWords = function(str) {
		if (!str) return '';
		var words = str.split(' ');
		if (words.length > 5) {
			return words.slice(0, 5).join(' ');
		}
		return str;
	};


	$scope.brandImageChanged = function(files) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$scope.$apply(function() {
				$scope.newBrandImageURL = e.target.result;
			});
		};
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
				// Lưu đường dẫn ảnh vào biến form.anh
				$scope.form.anhTH = resp.data.name;
			}
		}).catch(error => {
			alert("Lỗi tải ảnh!");
			console.log("Error", error);
		});
	};


	$scope.themGH = function(item) {
	    $http.post('/rest/giohang/ghtontai', item).then(resp => {
	        $scope.giohang = resp.data;

	        // Hiển thị thông báo thành công
	        Swal.fire({
	            icon: 'success',
	            title: 'Thành công!',
	            text: 'Sản phẩm đã được thêm vào giỏ hàng!',
	            confirmButtonText: 'OK'
	        }).then(() => {
	            // Đóng modal và reset nội dung
	            $('#quickViewModal').modal('hide'); // Sử dụng đúng ID của modal
	            $scope.resetModal();

	            // Thực hiện các hành động khác nếu cần
	            $scope.initialize();
	        });
	    }).catch(error => {
	        console.error("Lỗi khi thêm sản phẩm vào giỏ hàng: ", error);
	        // Hiển thị thông báo lỗi
	        Swal.fire({
	            icon: 'error',
	            title: 'Có lỗi xảy ra!',
	            text: 'Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng!',
	            confirmButtonText: 'OK'
	        });
	    });
	};

	// Hàm để reset nội dung modal
	$scope.resetModal = function() {
	    $scope.form = {};  // Reset form nếu form là một object chứa dữ liệu modal
	};



	$scope.pager = {
		page: 0,
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

	$scope.showAll = function() {
		$scope.pager.size = $scope.items.length;
		$scope.pager.page = 0;
	};

	$scope.sortOptions = [
		{ value: 'newest', text: 'Mới nhất' },
		{ value: 'best_selling', text: 'Bán chạy' },
		{ value: 'price_asc', text: 'Giá tăng dần' },
		{ value: 'price_desc', text: 'Giá giảm dần' }
	];

	$scope.selectedSortOption = $scope.sortOptions[0].value; // Giá trị mặc định

	$scope.sortProducts = function() {
		let sortedItems = angular.copy($scope.items);

		if ($scope.selectedSortOption === 'price_asc') {
			sortedItems.sort((a, b) => a.gia - b.gia);
		} else if ($scope.selectedSortOption === 'price_desc') {
			sortedItems.sort((a, b) => b.gia - a.gia);
		} else if ($scope.selectedSortOption === 'newest') {
			sortedItems.sort((a, b) => new Date(b.createDate) - new Date(a.createDate));
		} else if ($scope.selectedSortOption === 'best_selling') {

		}

		$scope.items = sortedItems;
		$scope.pager.page = 0;
	};

	$scope.$watch('selectedSortOption', function(newVal) {
		$scope.sortProducts();
	});

	$scope.initialize();
	$scope.reset();

});
