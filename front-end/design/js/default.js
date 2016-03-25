$(document).ready(function(){

  // smooth scroll
  $('a[href*=#]').click(function(e) {
    e.preventDefault();
    if ($(this.hash).offset()) {
      $('html,body').animate({scrollTop: $(this.hash).offset().top}, 400);
    }
  });

  // init Isotope
  var $grid = $('.grid').isotope({
    itemSelector: '.grid-item',
    percentPosition: true,
    masonry: {
      columnWidth: '.grid-sizer'
    }
  });

  // filter items on button click
  $('.filter').on( 'click', 'button', function() {
    var filterValue = $(this).attr('data-filter');
    $('.filter .btn').removeClass('active');
    $(this).addClass('active');
    $grid.isotope({ filter: filterValue });
  });

});