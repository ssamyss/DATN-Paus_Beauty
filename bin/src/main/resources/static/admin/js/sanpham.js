const app = angular.module("app", []);
app.controller("sanpham-ctrl", function($scope, $http) {
	
    $scope.form = {};
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


	$scope.create = function() {
		var item = angular.copy($scope.form);
		$http.post('/rest/products', item).then(resp => {
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
		$http.put('/rest/products/' + item.id, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("Cập nhật sản phẩm thành công");
		}).catch(error => {
			alert("Lỗi cập nhật!");
			console.log("Error", error);
		});
	};
	
	$scope.delete = function(item) {
		$http.delete('/rest/products/' + item.id).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
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
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/product', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
		}).catch(error => {
			alert("Lỗi tải ảnh!");
			console.log("Error", error);
		});
	};

    $scope.initialize();
    $scope.reset();
});
