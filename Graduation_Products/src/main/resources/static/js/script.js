// basic-N39 [wClWbEGra3]
(function() {
  $(function() {
    $(".basic-N39").each(function() {
      const $block = $(this);
      // Swiper
      const swiper = new Swiper(".basic-N39 .contents-swiper", {
        slidesPerView: 1,
        spaceBetween: 0,
        allowTouchMove: false,
        loop: true,
        autoplay: {
          delay: 5000,
        },
        loop: true,
        pagination: {
          el: ".basic-N39 .swiper-pagination",
          type: "fraction",
          clickable: true,
        },
        navigation: {
          nextEl: ".basic-N39 .swiper-button-next",
          prevEl: ".basic-N39 .swiper-button-prev",
        },
      });
      // Swiper Play, Pause Button
      const pauseButton = $block.find('.swiper-button-pause');
      const playButton = $block.find('.swiper-button-play');
      playButton.hide();
      pauseButton.show();
      pauseButton.on('click', function() {
        swiper.autoplay.stop();
        playButton.show();
        pauseButton.hide();
      });
      playButton.on('click', function() {
        swiper.autoplay.start();
        playButton.hide();
        pauseButton.show();
      });
    });
  });
})();
// basic-N42 [DclWBeGrY4]
(function() {
  $(function() {
    $(".basic-N42").each(function() {
      const $block = $(this);
      // Swiper
      const swiper = new Swiper(".basic-N42 .contents-swiper", {
        slidesPerView: 'auto',
        spaceBetween: 0,
        allowTouchMove: false,
        loop: true,
        autoplay: {
          delay: 5000,
        },
        navigation: {
          nextEl: ".basic-N42 .swiper-button-next",
          prevEl: ".basic-N42 .swiper-button-prev",
        },
        pagination: {
          type: "progressbar",
          el: ".basic-N42 .swiper-pagination",
          clickable: true,
        },
      });
    });
  });
})();
// basic-N9 [salWbeiEbY]
(function() {
  $(function() {
    $(".basic-N9").each(function() {
      const $block = $(this);
      // Swiper
      const swiper = new Swiper(".basic-N9 .contents-swiper", {
        slidesPerView: 'auto',
        allowTouchMove: false,
        spaceBetween: 0,
        loop: true,
        navigation: {
          nextEl: ".basic-N9 .swiper-button-next",
          prevEl: ".basic-N9 .swiper-button-prev",
        },
      });
    });
  });
})();