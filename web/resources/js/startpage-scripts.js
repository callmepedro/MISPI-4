function showTimeAndDate(){
    let date = new Date();
    let month = date.getMonth();
    let day = date.getDate();
    let hours = date.getHours()
    let minutes = date.getMinutes()
    let seconds = date.getSeconds()

    hours = (hours < 10) ? "0" + hours : hours
    minutes = (minutes < 10) ? "0" + minutes : minutes
    seconds = (seconds < 10) ? "0" + seconds : seconds
    month = (month < 10) ? "0" + month : month
    day = (day < 10) ? "0" + day : day

    let time = hours + ":" + minutes + ":" + seconds + "  " + day + "/" + month
    document.getElementById("start-clock").textContent = time

    setTimeout(showTimeAndDate, 8000);
}