const app = angular.module("app", []);
app.controller("sanpham-index", function($scope, $http) {
    $scope.form = {};
    $scope.dmucsp = [];
    $scope.cates = [];
    $scope.bras = [];
    $scope.items = [];

    $scope.reset = function() {
        $scope.form = {
            createDate: new Date(),
        };
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
	   
    $scope.initialize();
    $scope.reset();
});
