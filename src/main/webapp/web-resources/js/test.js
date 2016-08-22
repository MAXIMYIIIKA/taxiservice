/**
 * Created by maxim on 21.08.2016.
 */

var currentNumberOfOrders = 0;




// $(document).ready(function () {
//
// })

$(document).ready(function() {
   $('#test').click(addString);
});

$(document).ready(function () {
   $('#check').click(checkNewOrders);
});

$(document).ready(function () {
    $('#new-order').click(showStrings);
});


var addString = function () {
        $.get("test/ajaxAddString");
};

var showStrings = function () {
        $.get("test/ajaxShowStrings",function(data, status){
            $(".test").remove();
            $("#strings").append(function () {
                var string = "";
                currentNumberOfOrders = 0;
                data.strings.forEach(function (item, index) {
                    string += "<tr class='test'><td>" + item + "</td></tr>";
                    currentNumberOfOrders++;
                });
                return string;
            });
        });
};

var checkNewOrders = function() {
    // $(document).ready(function () {
       $.get("test/ajaxCheck?currentNumberOfOrders=" + currentNumberOfOrders, function (data, status) {
           if (data === 'Yes') {
               $('#new-order-sound').html('<audio style="display:none;" autoplay="autoplay"><source src="resources/sound/new_order.mp3" type="audio/mpeg"></audio>');
               showStrings();
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
           }
       });
    // });
};

$(document).ready(showStrings);
$(document).ready(checkNewOrders());