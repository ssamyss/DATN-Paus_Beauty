var data = {
	labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5",
		"Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10",
		"Tháng 11", "Tháng 12"],
	datasets: [{
		label: "Dữ liệu đầu tiên",
		fillColor: "rgba(255, 255, 255, 0.158)",
		strokeColor: "black",
		pointColor: "rgb(220, 64, 59)",
		pointStrokeColor: "#fff",
		pointHighlightFill: "#fff",
		pointHighlightStroke: "green",
		data: [20, 59, 90, 51, 56, 100, 40, 60, 78, 53, 33, 81]
	}, {
		label: "Dữ liệu kế tiếp",
		fillColor: "rgba(255, 255, 255, 0.158)",
		strokeColor: "rgb(220, 64, 59)",
		pointColor: "black",
		pointStrokeColor: "#fff",
		pointHighlightFill: "#fff",
		pointHighlightStroke: "green",
		data: [48, 48, 49, 39, 86, 10, 50, 70, 60, 70, 75, 90]
	}]
};

var ctxl = $("#lineChartDemo").get(0).getContext("2d");
var lineChart = new Chart(ctxl).Line(data);

var ctxb = $("#barChartDemo").get(0).getContext("2d");
var barChart = new Chart(ctxb).Bar(data);

if (document.location.hostname == 'pratikborsadiya.in') {
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script',
		'//www.google-analytics.com/analytics.js', 'ga');
	ga('create', 'UA-72504830-1', 'auto');
	ga('send', 'pageview');
}

document.addEventListener('DOMContentLoaded', (event) => {
       document.querySelectorAll('.gia').forEach(function(element) {
           let value = parseFloat(element.textContent);
           let formattedValue = new Intl.NumberFormat('vi-VN').format(value);
           element.textContent = formattedValue + " VND";
       });
   });


//Thời Gian
function time() {
	var today = new Date();
	var weekday = new Array(7);
	weekday[0] = "Chủ Nhật";
	weekday[1] = "Thứ Hai";
	weekday[2] = "Thứ Ba";
	weekday[3] = "Thứ Tư";
	weekday[4] = "Thứ Năm";
	weekday[5] = "Thứ Sáu";
	weekday[6] = "Thứ Bảy";
	var day = weekday[today.getDay()];
	var dd = today.getDate();
	var mm = today.getMonth() + 1;
	var yyyy = today.getFullYear();
	var h = today.getHours();
	var m = today.getMinutes();
	var s = today.getSeconds();
	m = checkTime(m);
	s = checkTime(s);
	nowTime = h + " giờ " + m + " phút " + s + " giây";
	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	today = day + ', ' + dd + '/' + mm + '/' + yyyy;
	tmp = '<span class="date"> ' + today + ' - ' + nowTime + '</span>';
	document.getElementById("clock").innerHTML = tmp;
	clocktime = setTimeout("time()", "1000", "Javascript");

	function checkTime(i) {
		if (i < 10) {
			i = "0" + i;
		}
		return i;
	}
}