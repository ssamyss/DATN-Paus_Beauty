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

    $scope.initialize();
    $scope.reset();
});
