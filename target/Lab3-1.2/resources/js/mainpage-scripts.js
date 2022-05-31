function checkHitResult(x, y, r) {
    return checkTriangle(x, y, r) || checkRectangle(x, y, r) || checkCircle(x, y, r)
}

function checkTriangle(x, y, r){
    return x >= 0 && y <= 0 && 2*(x-y) <= r
}

function checkRectangle(x, y, r){
    return x >= -r && x <= 0 && y >= 0 && y <= r
}

function checkCircle(x, y, r){
    return x >= 0 && y >= 0 && x*x + y*y <= r*r
}


$(document).ready(function() {
    $("#clear-button").click(function() {
        $('.svg-point').remove();
    })

    $('#svg-graph').on('click', function (event) {
        let pt = this.createSVGPoint();
        pt.x = event.clientX;
        pt.y = event.clientY;
        let cursor_pt = pt.matrixTransform(this.getScreenCTM().inverse());

        let r_value = document.getElementById('main-form:r-value').value;
        let x_value = (cursor_pt.x - 130) / 100 * r_value
        let y_value = (130 - cursor_pt.y) / 100 * r_value

        x_value = x_value.toFixed(5);
        y_value = y_value.toFixed(5);

        document.getElementById('svg-points-form:svg-x-value').value = x_value
        document.getElementById('svg-points-form:svg-y-value').value = y_value
        document.getElementById('svg-points-form:svg-r-value').value = r_value;
        document.getElementById('svg-points-form:hidden-button').click();

        let hitResult = checkHitResult(x_value, y_value, r_value)
        drawPoint(cursor_pt.x, cursor_pt.y, hitResult, 2.5)
    })


    function drawPoint(x, y, hit, radius) {
        let element = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
        element.setAttribute('class', "svg-point")
        element.setAttribute('cx', x)
        element.setAttribute('cy', y)
        element.setAttribute('r', radius)
        if (hit) {
            let successColor = '#21f38a'
            element.setAttribute('fill', successColor)
        } else {
            let failureColor = '#607D8B'
            element.setAttribute('fill', failureColor)
        }

        document.getElementById("svg-graph").appendChild(element)
    }
});
