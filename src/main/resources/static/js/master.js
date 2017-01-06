(function($) {
    var $document = $(document);

    // When the page is fully loaded
    $document.ready(function() {
        $.ajax({
            url : '/notification/count',
            method: 'GET',
            success : function(response) {
                var notificationCount = response.count;
                if (response.success === false)  {
                    notificationCount = 0;
                }
                if (notificationCount > 0) $('#nav-mobile').find('.notifications').append($('<span class="new badge">' + notificationCount  + '</span>'));
            }
        });
    });
})(jQuery);