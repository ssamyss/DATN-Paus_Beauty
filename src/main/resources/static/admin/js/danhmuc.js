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

	//Thương hiệu

	$scope.createTH = function() {
		var item = angular.copy($scope.form);
		$http.post('/rest/thuonghieu', item).then(resp => {
			$scope.items.push(resp.data);
			Swal.fire({
				icon: 'success',
				title: 'Thành công',
				text: 'Thêm sản phẩm thành công!',
				confirmButtonText: 'OK',
				confirmButtonColor: '#28a745'
			});
			$scope.reset();
			$scope.initialize();
		}).catch(error => {
			alert("Lỗi thêm mới!");
			console.log("Error", error);
		});
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


	$scope.initialize();
	$scope.reset();

});