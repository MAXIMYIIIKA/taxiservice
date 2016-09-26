/**
 * Created by maxim on 06.09.2016.
 */
function visible() {
    setTimeout(show, 50);
    setTimeout(hide, 1800);
};

function show() {
    $('#parent-alert').addClass("visible");
}

function hide(){
    $('#parent-alert').removeClass("visible");
};

document.addEventListener("click", function () {
    var a = document.getElementById("parent-alert");
    if (a != null) {
        a.addEventListener("click", hide);
    }
});

// $(document).on("scroll", function () {
//     var wrap = $("#upperPanel");
//
//     // wrap.on("scroll", function(e) {
//
//         if (wrap.getBoundingClientRect().top < 0) {
//             wrap.addClass("sticky");
//         } else {
//             wrap.removeClass("sticky");
//         }
//
//     // });
// });


// var menuIsFixed = false;
// var wrap = $("#upperPanel");
// var body = $("body");
// var html = $(document);
//
// window.addEventListener('scroll', function() {
//
//     var isScrollFollowHeader = (body.scrollTop || html.scrollTop) >= 30;
//     if(menuIsFixed != isScrollFollowHeader) {
//         wrap[menuIsFixed ? 'classRemove' : 'classAdd']('sticky');
//         menuIsFixed = !menuIsFixed;
//     }
// });



// (function(){
//     var a = document.querySelector('#upperPanel'), b = null;
//     window.addEventListener('scroll', Ascroll, false);
    // document.body.addEventListener('scroll', Ascroll, false);  // если у html и body высота равна 100%
    // function Ascroll() {
    //     a = document.querySelector('#upperPanel');
        // if (b == null) {  // добавить потомка-обёртку, чтобы убрать зависимость с соседями
        //     var Sa = getComputedStyle(a, ''), s = '';
        //     for (var i = 0; i < Sa.length; i++) {  // перечислить стили CSS, которые нужно скопировать с родителя
        //         if (Sa[i].indexOf('overflow') == 0 || Sa[i].indexOf('padding') == 0 || Sa[i].indexOf('border') == 0 || Sa[i].indexOf('outline') == 0 || Sa[i].indexOf('box-shadow') == 0 || Sa[i].indexOf('background') == 0) {
        //             s += Sa[i] + ': ' +Sa.getPropertyValue(Sa[i]) + '; '
        //         }
        //     }
        //     b = document.createElement('div');  // создать потомка
        //     b.style.cssText = s + ' box-sizing: border-box; width: ' + a.offsetWidth + 'px;';
        //     a.insertBefore(b, a.firstChild);  // поместить потомка в цепляющийся блок первым
        //     var l = a.childNodes.length;
        //     for (var i = 1; i < l; i++) {  // переместить во вновь созданного потомка всех остальных потомков (итого: создан потомок-обёртка, внутри которого по прежнему работают скрипты)
        //         b.appendChild(a.childNodes[1]);
        //     }
        //     a.style.height = b.getBoundingClientRect().height + 'px';  // если под скользящим элементом есть другие блоки, можно своё значение
        //     // a.style.padding = '0';
        //     // a.style.border = '0';  // если элементу присвоен padding или border
        // }
        // if (a.getBoundingClientRect().top < 0) { // elem.getBoundingClientRect() возвращает в px координаты элемента относительно верхнего левого угла области просмотра окна браузера
        //     a.className = 'sticky';
            // a.style.background = 'none';
        // } else {
        //     a.className = '';
            // a.style.background = 'rgba(0, 0, 0, 0.66)';
        // }
        // window.addEventListener('resize', function() {
        //     a.children[0].style.width = getComputedStyle(a, '').width
        // }, false);  // если изменить размер окна браузера, измениться ширина элемента
//     }
// })();