/**
 * Created by maxim on 21.08.2016.
 */

var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");
var csrfToken = $("meta[name='_csrf']").attr("content");


var showOrders = function () {
        $.get("dispatch/ajaxShowOrders",function(data, status){
            $(".newOrder").remove();
            $("#new-orders").append(function () {
                var string = "";
                data.orders.forEach(function (order, index) {
                    string += "<tr class='newOrder'>"
                                + "<td>" + order.orderId + "</td>"
                                + "<td>" + order.username + "</td>"
                                + "<td>" + order.currentLocation.degreesMinutesSeconds +  "</td>"
                                + "<td>" + order.targetLocation.degreesMinutesSeconds + "</td>"
                                + "<td>" + order.dateTime.date + " " + order.dateTime.time + "</td>"
                                + "<td>" + order.status + "</td>"
                                + "<td>" + order.phone + "</td>";
                    if (order.status == "PROCESSING") {
                        string += "<td><button class='acceptButton' name='" + order.orderId + "' value='" + order.orderId + "'>Accept</button></td>"
                        + "<td><button class='denyButton' name='" + order.orderId + "' value='" + order.orderId + "'>Deny</button></td>";
                    }
                    string += "</tr>";
                });
                return string;
            });
            $('.acceptButton').click(function () {
                var data = {};
                data[csrfParameter] = csrfToken;
                data["orderId"] = this.getAttribute("value");
                data["status"] = "ACCEPTED";
                $.ajax({
                    type: "POST",
                    url: "dispatch/ajaxChangeOrderStatus",
                    data: data
                });
            });
            $('.denyButton').click(function () {
                var data = {};
                var headers = {};
                headers[csrfHeader] = csrfToken;
                data[csrfParameter] = csrfToken;
                data["orderId"] = this.getAttribute("value");
                data["status"] = "DENIED";
                $.ajax({
                    type: "POST",
                    url: "dispatch/ajaxChangeOrderStatus",
                    data: data
                    // headers: headers
                });
            });
        });
};

var checkNewOrders = function() {
       $.get("dispatch/ajaxCheck", function (data, status) {
           if (data === 'newOrder') {
               $('#new-order-sound').html('<audio style="display:none;" autoplay="autoplay"><source src="resources/sound/new_order.mp3" type="audio/mpeg"></audio>');
               showOrders();
               checkNewOrders();
               $('#new-order').animate({
                   opacity: 1,
                   top: "25%",
               }, 1000, function() {
                           $('#new-order').data('timeout', setTimeout( function () {
                               $('#new-order').animate({
                                   opacity: 0,
                                   top: "-10%"
                               },1000);
                           }, 2000));
               });
           } else if (data == 'newStatus') {
               showOrders();
               checkNewOrders();
           }
       }).fail(function () {
           setTimeout(checkNewOrders(), 5000);
       });
};

$(document).ready(showOrders);
$(document).ready(checkNewOrders());