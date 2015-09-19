;
(function () {
    // load data about right table
    var $predictionTable = $('div.main-body div.right-page table');
    $.each(window['prediction']['positionalPredictions'], function (key, values) {
        var position = parseInt(key);
        for (var i = 0, len = values.length; i < len; i++) {
            addRecommendNumber(position, values[i]);
        }
    });
    function addRecommendNumber(position, n) {
        var text = n < 10 ? '0' + n : '' + n;
        $predictionTable.find('tr').eq(position).find('span.ball').eq(n - 1)
            .addClass('recommend').text(text);
    }
})();
(function () {
    // set events about position select radio
    $('div.main-body div.left-page div.position input[type=radio]').click(function () {
        location.href = JSUtils.updateUrlParam('evaluatePosition', $(this).val());
    }).each(function () {
        if (this.checked) {
            var evaluatePosition = $(this).val();
            $('div.main-body div.left-page tbody tr').each(function () {
                $(this).find('span').eq(evaluatePosition - 1).addClass('evaluated');
            });
        }
    });

})();
(function () {
    var sum = 0;
    $('div.main-body div.left-page table tbody tr').each(function () {
        var $this = $(this);
        var phase = parseInt($this.find('td.phase').text());
        var evaluation = window['evaluation'][phase];
        if (evaluation == null) {
            return true;
        }

        $.each(evaluation['predictValues'], function (key, value) {
            var text = value < 10 ? '0' + value : '' + value;
            var $ball = $this.find('span.predict').eq(value - 1);
            if (evaluation['realValue'] == value) {
                $ball.removeClass('predict').text(text);
            } else {
                $ball.addClass('recommend').text(text);
            }
        });

        var cssClass = evaluation['win'] ? 'win' : 'lose';
        var $revenue = $this.find('td.revenue').addClass(cssClass);
        var $status = $this.find('td.status').addClass(cssClass);
        var revenue = evaluation['revenue'];
        $revenue.text(revenue);
        $status.text(evaluation['win'] ? '中' : '挂');

        sum += revenue;
        var $sum = $this.find('td.sum').text(sum);
        $sum.addClass(sum >= 0 ? 'win' : 'lose');
    });
})();
(function () {
    // TODO load data by ajax
    // reload page with new data downloaded
    setInterval(function () {
        $.post('max-phase.json', {}, function (data) {
            var maxPhaseInPage = parseInt($('div.main-body div.left-page table tbody tr td.phase').eq(0).text());
            if (parseInt(data['phase']) > maxPhaseInPage) {
                if (!hasVisibleFloatPanel()) {
                    location.reload();
                }
            }
        });
    }, 5000); // reload each five seconds

    function hasVisibleFloatPanel() {
        var $tb = $('#transparentBackground');
        return $tb.size() > 0 && $tb.css('display') != 'none';
    }
})();