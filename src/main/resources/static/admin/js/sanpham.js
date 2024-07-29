const app = angular.module("app", []);
app.controller("sanpham-ctrl", function($scope, $http) {
	$scope.form = {};
	$scope.dmucsp = [];
	$scope.cates = [];
	$scope.bras = [];
	$scope.items = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
			moTa: ''
			// Các trường khác của form
		};
		$scope.imageURL = ''; // Đặt lại đường dẫn ảnh về trống để không hiển thị ảnh
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
	};

	$scope.loadProductTypes = function(categoryId) {
		if (categoryId) {
			$http.get(`/rest/loaisanpham/by-category/${categoryId}`).then(function(response) {
				$scope.cates = response.data;
			}, function(error) {
				console.error('Error fetching product types:', error);
			});
		} else {
			$scope.cates = [];
		}
	};

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	}

	$scope.create = function() {
		// Cập nhật giá trị CKEditor vào ng-model
		$scope.form.mota = CKEDITOR.instances.mota.getData();

		// Xử lý mô tả để loại bỏ các thẻ <p>
		$scope.form.mota = CKEDITOR.instances.mota.getData().replace(/<\/?[^>]+(>|$)/g, "");
		var item = angular.copy($scope.form);
		$http.post('/rest/sanpham', item).then(resp => {
			$scope.items.push(resp.data);
			Swal.fire({
				icon: 'success',
				title: 'Thành công',
				text: 'Thêm sản phẩm thành công!',
				confirmButtonText: 'OK',
				confirmButtonColor: '#28a745'
			});
			CKEDITOR.instances.mota.setData('');
			$scope.reset();
		}).catch(error => {
			alert("Lỗi thêm mới!");
			console.log("Error", error);
		});
	};

	$scope.update = function() {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn cập nhật sản phẩm không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Cập nhật',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				if ($scope.form.tonKho === 0) {
					$scope.form.trangThai = false;
				} else {
					$scope.form.trangThai = true;
				}
				$scope.form.createDate = new Date().toISOString();
				var item = angular.copy($scope.form);
				$http.put('/rest/sanpham/' + item.maSP, item).then(resp => {
					var index = $scope.items.findIndex(p => p.maSP == item.maSP);
					$scope.items[index] = angular.copy(item);
					$('#ModalUP').modal('hide');
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Cập nhật sản phẩm thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
				}).catch(error => {
					alert("Lỗi cập nhật!");
					console.log("Error", error);
				});
			}
		});
	};


	//xóa tất cả sản phẩm
	$scope.deleteAll = function() {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn xóa tất cả sản phẩm không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				// Call the delete function if the user confirms
				$http.delete('/rest/sanpham').then(resp => {
					$scope.items = []; // Clear the local list of items
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Sản phẩm đã được xóa thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
					$scope.initialize(); // Refresh the data to ensure UI is updated
				}).catch(error => {
					alert("Lỗi khi xóa tất cả sản phẩm!");
					console.log("Error", error);
				});
			}
		});
	};


	//xóa sản phẩm theo mã sản phẩm
	$scope.delete = function(item) {
		Swal.fire({
			title: 'Xác nhận',
			text: "Bạn có chắc chắn muốn xóa sản phẩm này không?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Xóa',
			cancelButtonText: 'Hủy'
		}).then((result) => {
			if (result.isConfirmed) {
				// Call the delete function if the user confirms
				$http.delete('/rest/sanpham/' + item.maSP, item).then(resp => {
					var index = $scope.items.findIndex(p => p.maSP == item.maSP);
					$scope.items.splice(index, 1);
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Sản phẩm đã được xóa thành công!',
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
		$http.post('/rest/upload/sanpham', data, {
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

	//Thương hiệu

	$scope.createTH = function() {
		var item = angular.copy($scope.form);
		$http.post('/rest/thuonghieu', item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			alert("Thêm mới thương hiệu thành công");
		}).catch(error => {
			alert("Lỗi thêm mới!");
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

	// Lấy 5 chữ đầu
	$scope.getFirstFiveWords = function(str) {
		if (!str) return '';
		var words = str.split(' ');
		if (words.length > 5) {
			return words.slice(0, 5).join(' ');
		}
		return str;
	};

	$scope.themGH = function(item) {
		$http.post('/rest/giohang', item).then(resp => {
			$scope.giohang.push(resp.data);
			alert("Sản phẩm đã được thêm vào giỏ hàng");
		}).catch(error => {
			alert("Lỗi thêm sản phẩm!");
			console.log("Error", error);
		});
	};


	// Thêm dữ liệu từ file Excel
	$scope.import = function(files) {
		var reader = new FileReader();
		reader.onloadend = async () => {
			var workbook = new ExcelJS.Workbook();
			await workbook.xlsx.load(reader.result);
			const worksheet = workbook.getWorksheet('Sheet1');
			let successCount = 0; // Counter to keep track of successful imports

			worksheet.eachRow((row, index) => {
				if (index > 1) {
					let sanPham = {
						maSP: row.getCell(1).value,
						tenSP: row.getCell(6).value,
						gia: row.getCell(4).value,
						tonKho: row.getCell(7).value,
						mota: row.getCell(5).value,
						anh: row.getCell(2).value,
						trangThai: row.getCell(8).value,
						createDate: new Date(row.getCell(3).value),
						thuongHieu: { maTH: row.getCell(10).value },
						loaiSanPham: { maPL: row.getCell(9).value }
					};

					var url = "http://localhost:8080/rest/sanpham";
					$http.post(url, sanPham).then(resp => {
						console.log("Success", resp.data);
						$scope.initialize();
						successCount++; // Increment the counter for each successful import
					}).catch(error => {
						console.log("Error", error);
					});
				}
			});

			// Show success notification after all rows have been processed
			setTimeout(() => {
				if (successCount > 0) {
					Swal.fire({
						icon: 'success',
						title: 'Thành công',
						text: 'Thêm sản phẩm thành công!',
						confirmButtonText: 'OK',
						confirmButtonColor: '#28a745'
					});
				}
			}, 1000); // Delay to ensure all requests are processed
		};
		reader.readAsArrayBuffer(files[0]);
	};
	
	$scope.pager = {
			page:0,
			size:5,
			get items(){
				var start =this.page*this.size;
				return $scope.items.slice(start,start+this.size);
			},
			get count(){
				return Math.ceil(1.0*$scope.items.length/this.size);
			},
			first(){
				this.page=0;
			},
			prev(){
				this.page--;
				if(this.page<0){
					this.last();
				}
			},
			next(){
				this.page++;
				if(this.page >= this.count){
					this.first();
				}
			},
			last(){
				this.page =  this.count -1;
			}

		}

	$scope.initialize();
	$scope.reset();
});
