const app = angular.module("app", []);
app.controller("baocao-ctrl", function($scope, $http) {
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
		$http.get("/rest/sanpham/tonKho").then(resp => {
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

	// Lấy 5 chữ đầu
	$scope.getFirstFiveWords = function(str) {
		if (!str) return '';
		var words = str.split(' ');
		if (words.length > 5) {
			return words.slice(0, 5).join(' ');
		}
		return str;
	};


	$scope.initialize();
	$scope.reset();


});

// doanh thu theo quý
document.addEventListener('DOMContentLoaded', function() {
	fetch('/rest/donhang/doanhthu/quy')
		.then(response => response.json())
		.then(data => {
			const labels = data.map(item => `Qúy ${item[0]}`);
			const revenueData = data.map(item => item[1]);

			const ctx = document.getElementById('quarterlyRevenueChart').getContext('2d');
			new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: '1 quý  = 3 tháng',
						data: revenueData,
						backgroundColor: 'rgba(54, 162, 235, 0.2)',
						borderColor: 'rgba(54, 162, 235, 1)',
						borderWidth: 1
					}]
				},
				options: {
					// 	                	plugins: {
					//                             legend: {
					//                                 display: false
					//                             }
					//                         },
					scales: {
						y: {
							beginAtZero: true
						}
					}
				}
			});
		})
		.catch(error => console.error('Error fetching quarterly revenue data:', error));
});


// doanh thu theo tháng
document.addEventListener('DOMContentLoaded', function() {
	fetch('/rest/donhang/doanhthu')
		.then(response => response.json())
		.then(data => {
			const labels = data.map(item => `Tháng ${item[0]}`);
			const revenues = data.map(item => item[1]);

			const ctx = document.getElementById('revenueChart').getContext('2d');
			new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: 'Doanh Thu Hàng Tháng',
						data: revenues,
						backgroundColor: 'rgba(75, 192, 192, 0.2)',
						borderColor: 'rgba(75, 192, 192, 1)',
						borderWidth: 1
					}]
				},
				options: {
					// 	                	plugins: {
					//                             legend: {
					//                                 display: false
					//                             }
					//                         },
					scales: {
						x: {
							beginAtZero: true
						},
						y: {
							beginAtZero: true
						}
					}
				}
			});
		})
		.catch(error => console.error('Error fetching monthly revenue data:', error));
});