const app = angular.module("app", []);

app.controller("thongtincanhan-ctrl", function($scope, $http) {
    $scope.form = {};
    $scope.taikhoan = {};
    $scope.giohang = [];
    $scope.tongtien = 0;
    $scope.dem = 0;

    // Reset form to its initial state
    $scope.reset = function() {
        $scope.form = {
            createDate: new Date(),
        };
    };

    // Initialize function to load user and cart data
    $scope.initialize = function() {
        // Load logged-in user's account information
        $http.get("rest/taikhoan/tentaikhoan").then(resp => {
            $scope.taikhoan = resp.data;

            // Load the user's cart based on their username
            $http.get('/rest/giohang/byttk/' + $scope.taikhoan.tenTaiKhoan).then(resp => {
                $scope.giohang = resp.data;
                $scope.dem = $scope.giohang.length;
            }).catch(error => {
                alert("Lỗi khi tải giỏ hàng!");
                console.error("Error loading cart:", error);
            });
        }).catch(error => {
            console.error("Lỗi khi tải tên tài khoản:", error);
        });
    };

    // Save the updated user information
    $scope.saveTaiKhoan = function() {
        $http.put("/rest/taikhoan", $scope.taikhoan).then(resp => {
            alert("Thông tin cá nhân đã được cập nhật thành công!");
        }).catch(error => {
            alert("Có lỗi xảy ra khi lưu thông tin!!!");
            console.error("Error saving user information:", error);
        });
    };

    // Initialize and reset forms
    $scope.initialize();
    $scope.reset();
});
