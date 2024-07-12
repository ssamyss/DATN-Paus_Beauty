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
	        moTa: '',
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

        var item = angular.copy($scope.form);
        $http.post('/rest/sanpham', item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            alert("Thêm mới sản phẩm thành công");
        }).catch(error => {
            alert("Lỗi thêm mới!");
            console.log("Error", error);
        });
    };

	$scope.update = function() {
	    var item = angular.copy($scope.form);
	    $http.put('/rest/sanpham/' + item.maSP, item).then(resp => {
	        // Update the item in the items array
	        var index = $scope.items.findIndex(p => p.maSP == item.maSP);
	        $scope.items[index] = angular.copy(item);
	        $('#ModalUP').modal('hide'); // Hide the modal after successful update
	        alert("Cập nhật sản phẩm thành công");
	    }).catch(error => {
	        alert("Lỗi cập nhật!");
	        console.log("Error", error);
	    });
	};


    $scope.delete = function(item) {
        $http.delete('/rest/sanpham/' + item.maSP, item).then(resp => {
            var index = $scope.items.findIndex(p => p.maSP == item.maSP);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Xóa sản phẩm thành công");

            // Gọi lại hàm để cập nhật dữ liệu từ server
            $scope.initialize();
        }).catch(error => {
            alert("Lỗi xóa dữ liệu!");
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

    $scope.initialize();
    $scope.reset();
});
