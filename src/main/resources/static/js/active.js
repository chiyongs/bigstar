$(document).ready(() => {
  var menus = document.getElementsByClassName("menu");
  var pages = $(".page");
  var activepages = $(".activePage");

  for (var i = 0; i < menus.length; i++) {
    menus[i].addEventListener("click", function () {
      var current = $("#active");
      current.removeAttr("id");

      $(this).attr("id", "active");

      let activePage = $(".activePage");
      activePage.attr("class", "page");
      $("ul li").click(function () {
        if ($(this).index() != 0) {
          pages.eq($(this).index() - 1).attr("class", "activePage");
        } else if ($(this).index() == 0) {
          activepages.eq($(this).index()).attr("class", "activePage");
        }
      });
      //   pages.eq($(this).index() - 1).attr("class", "activePage");
      //     console.log("li");
      //   console.log($("li").index());
    });
  }
});
