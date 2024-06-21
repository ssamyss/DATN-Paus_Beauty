const app = angular.module("app", []);
app.controller("sanpham-ctrl", function($scope, $http) {
	$scope.form = {};
    $scope.cates= [];
    $scope.bras = [];
	$scope.items = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	}

	$scope.initialize = function() {
		//load sản phẩm
		$http.get("/rest/sanpham").then(resp => {
			$scope.items = resp.data;
			$scope.items.forEach(item => {
				item.createDate = new Date(item.createDate)
			});
			console.log("Success", resp);
		});
	
		$http.get("/rest/loaisanpham").then(resp => {
			$scope.cates = resp.data;
			console.log("Success", resp.data);
		});
	
		$http.get("/rest/thuonghieu").then(resp => {
			$scope.bras = resp.data;
			console.log("Success", resp.data);
		})
	};
	

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs button:eq(0)").tab('show')
	}
	


	$scope.create = function() {
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
		$http.put('/rest/sanpham/' + item.id, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("Cập nhật sản phẩm thành công");
		}).catch(error => {
			alert("Lỗi cập nhật!");
			console.log("Error", error);
		});
	};
	$scope.delete = function(item) {
		$http.delete('/rest/sanpham/' + item.id).then(resp => {
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


	// $scope.imageChanged = function(files) {
	// 	var data = new FormData();
	// 	data.append('file', files[0]);
	// 	$http.post('/rest/upload/product', data, {
	// 		transformRequest: angular.identity,
	// 		headers: { 'Content-Type': undefined }
	// 	}).then(resp => {
	// 		$scope.form.image = resp.data.name;
	// 	}).catch(error => {
	// 		alert("Lỗi tải ảnh!");
	// 		console.log("Error", error);
	// 	});
	// };



	// $scope.pager = {
	// 	page:0,
	// 	size:5,
	// 	get items(){
	// 		var start =this.page*this.size;
	// 		return $scope.items.slice(start,start+this.size);
	// 	},
	// 	get count(){
	// 		return Math.ceil(1.0*$scope.items.length/this.size);
	// 	},
	// 	first(){
	// 		this.page=0;
	// 	},
	// 	prev(){
	// 		this.page--;
	// 		if(this.page<0){
	// 			this.last();
	// 		}
	// 	},
	// 	next(){
	// 		this.page++;
	// 		if(this.page >= this.count){
	// 			this.first();
	// 		}
	// 	},
	// 	last(){
	// 		this.page =  this.count -1;
	// 	}

	// }

	$scope.initialize();
	$scope.reset();
});