const app = angular.module("app", []);
app.controller("quanlydonhang-ctrl", function($scope, $http) {
	$scope.accounts = [];
	$scope.donhang = [];

	$scope.reset = function() {
		$scope.form = {
			createDate: new Date(),
		};
	};

	$scope.initialize = function() {
		// Load đơn hàng
		$http.get('/rest/donhang/canhan').then(resp => {
			$scope.donhang = resp.data;
			$scope.donhang.forEach(item => {
				item.createDate = new Date(item.createDate);
			});
		}).catch(error => {
			console.error("Lỗi khi tải đơn hàng:", error);
		});
	};
	
	$scope.pager = {
		page: 0,
		size: 5,
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

	$scope.initialize();
	$scope.reset();
});
