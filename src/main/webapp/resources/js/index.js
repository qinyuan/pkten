;
(function () {
    var $predictionTable = $('div.main-body div.right-page table');

    function pad(n) {
        return n < 10 ? '0' + n : '' + n;
    }

    function addRecommendNumber(position, n) {
        $predictionTable.find('tr').eq(position).find('span.ball').eq(n - 1)
            .addClass('recommend').text(pad(n));
    }

    var predictions = window['prediction']['positionalPredictions'];

    for (var key in predictions) {
        if (!predictions.hasOwnProperty(key)) {
            continue;
        }
        var position = parseInt(key);
        var availableValues = predictions[key];
        for (var i = 0, len = availableValues.length; i < len; i++) {
            addRecommendNumber(position, availableValues[i]);
        }
    }
})();