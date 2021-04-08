function getBaseUrl() {
    return window.location.protocol + '//' + window.location.host;
}

function buildUrl(url) {
    return getBaseUrl() + url;
}

function ajax(methodType, url, successHandler) {
    $.ajax({
        type: methodType,
        url: buildUrl(url),
        success: successHandler,
        dataType: "json",
        contentType : "application/json"
    });
}

function ajaxWithData(methodType, url, data, successHandler) {
    $.ajax({
        type: methodType,
        url: buildUrl(url),
        data: JSON.stringify(data),
        success: successHandler,
        dataType: "json",
        contentType : "application/json",
    });
}

function syncGet(url) {
    return new Promise((resolve) => {
        ajax('GET', url, (data) => {
            resolve(data)
        });
    })
}

function get(url, successHandler) {
    ajax('GET', url, successHandler);
}

function httpDelete(url, successHandler) {
    ajax('DELETE', url, successHandler);
}

function post(url, data, successHandler) {
    ajaxWithData('POST', url, data, successHandler);
}

