const app = angular.module("app", []);
app.controller("sanpham-index", function($scope, $http, $location) {
	$scope.form = {};
	$scope.dmucsp = [];
	$scope.cates = [];
	$scope.bras = [];
	$scope.items = [];
	$scope.tentaikhoan = [];
	$scope.dem = 0;

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
			console.log("Sản phẩm:", resp.data);
		}).catch(error => {
			console.error("Lỗi khi tải sản phẩm:", error);
		});

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

		//Load tài khoản đăng nhập
		$http.get("rest/taikhoan/tentaikhoan").then(resp => {
			$scope.tentaikhoan = resp.data;
			//Load giỏ hàng
			$http.get('/rest/giohang/byttk/' + $scope.tentaikhoan.tenTaiKhoan).then(resp => {
				$scope.giohang = resp.data;
				$scope.giohang.forEach(item => {
					item.createDate = new Date(item.createDate);
				});
				$scope.dem = $scope.giohang.length;
			}).catch(error => {
				alert("Lỗi khi tải giỏ hàng!");
			});
		}).catch(error => {
			console.error(error);
		});

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

	$scope.themGH = function(item) {
		$http.post('/rest/giohang', item).then(resp => {
			$scope.giohang.push(resp.data);
			$('.js-modal1').removeClass('show-modal1');
			alert("Thêm sp vào giỏ hàng thành công!");
			$scope.reset();
		}).catch(error => {
			console.error("Lỗi khi thêm sp vào giỏ hàng: ", error);
		});
	};

	$scope.initialize();
	$scope.reset();

});
