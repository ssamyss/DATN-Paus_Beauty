const app = angular.module("app", []);
app.controller("chitietsanpham-ctl", function($scope, $http, $location, $window) {
	$scope.form = {};
	$scope.items = [];
	$scope.tentaikhoan = [];
	$scope.giohang = [];
	$scope.dem = 0;
	$scope.sanpham = [];



	$scope.reset = function() {
		$scope.form = {};
	};

	$scope.shuffleItems = function(array) {
	    for (let i = array.length - 1; i > 0; i--) {
	        const j = Math.floor(Math.random() * (i + 1));
	        [array[i], array[j]] = [array[j], array[i]];
	    }
	};

	// Pager object
	$scope.pager = {
	    page: 0,
	    size: 10, // Hiển thị 10 sản phẩm trên mỗi trang
	    get items() {
	        var start = this.page * this.size;
	        // Shuffle items before slicing
	        $scope.shuffleItems($scope.items);
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


		$http.get("/rest/sanpham/" + $window.location.search.split("=")[1]).then(resp => {
			$scope.sanpham = resp.data;
			console.log($location.absUrl().split("/")[4]);
			console.log($scope.sanpham);
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
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

	//Thêm nhanh
	$scope.themNhanh = function(item, successCallback, errorCallback) {  // Thêm successCallback và errorCallback
		$http.post('/rest/giohang/ghtontai', item).then(resp => {
			$scope.ghtontai = resp.data;
			var count = $scope.ghtontai.length;
			if (count === 0) {
				$http.post('/rest/giohang', item).then(resp => {
					$scope.giohang.push(resp.data);
					console.log("Sản phẩm đã được thêm vào giỏ hàng!"); // Log message không chặn
					$scope.initialize();
					$scope.reset();
					if (successCallback) successCallback();  // Gọi successCallback nếu có
				}).catch(error => {
					console.error("Lỗi khi thêm mới sản phẩm vào giỏ hàng: ", error);
					if (errorCallback) errorCallback();  // Gọi errorCallback nếu có lỗi
				});
			} else {
				$http.put('/rest/giohang/create2/' + $scope.ghtontai[0].maGH, $scope.ghtontai[0]).then(resp => {
					var index = $scope.giohang.findIndex(p => p.maGH == $scope.ghtontai[0].maGH);
					$scope.giohang[index].soLuong += 1;
					console.log("Thêm sản phẩm vào giỏ hàng thành công!"); // Log message không chặn
					if (successCallback) successCallback();  // Gọi successCallback nếu có
				}).catch(error => {
					console.error("Lỗi khi thêm sản phẩm vào giỏ hàng: ", error);
					if (errorCallback) errorCallback();  // Gọi errorCallback nếu có lỗi
				});
			}
		}).catch(error => {
			console.error("Lỗi khi lấy giỏ hàng tồn tại: ", error);
			if (errorCallback) errorCallback();  // Gọi errorCallback nếu có lỗi
		});
	};

	$scope.muangay = function(item) {
		$scope.themNhanh(item,
			function() {  // successCallback
				$window.location.href = "/cart";
			},
			function() {  // errorCallback
				alert("Không thể thêm sản phẩm vào giỏ hàng. Vui lòng thử lại sau."); // Hiển thị thông báo lỗi cho người dùng
			}
		);
	};

	// Thêm giỏ hàng
	$scope.themGH = function(item) {
		$http.post('/rest/giohang/ghtontai', item).then(resp => {
			$scope.giohang = resp.data;
			alert("Sản phẩm đã được thêm vào giỏ hàng!");
			$scope.initialize();
			$scope.reset();
		}).catch(error => {
			console.error("Lỗi khi thêm sản phẩm vào giỏ hàng: ", error);
			alert("Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng!");
		});
	};


	$scope.pager = {
		page: 0,
		size: 8, // Điều chỉnh số lượng sản phẩm trên mỗi trang
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
