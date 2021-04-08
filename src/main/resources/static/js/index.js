let monitoringRequest = new Map();

documentReady(async function () {
    await initializeMonitoringRequest();
    displayMonitoringRequest();
    addEventListenerFunction("form.add_monitoring_request_form", 'submit', addMonitoringRequest);
    setInterval(updateMonitoringRequest, 1800000);
});

function displayMonitoringRequest() {
    let iterator = 0;
    monitoringRequest.forEach(monitoringRequest => {
        iterator++;
        console.log(monitoringRequest);
        const markup = `
        <tr class="url_status">
            <td rowspan="3">${iterator}</td>
            <td><span class="fw-bold">URL:</span> ${monitoringRequest.monitoringRequestData.url}</td>
            <td>${monitoringRequest.monitoringResponseData.status}</td>
            <td><button type="button" class="btn btn-primary" value="${monitoringRequest.id}" onclick="deleteMonitoringRequest(this.value)">Delete</button> </td>
            <td><button type="button" class="btn btn-primary" value="${monitoringRequest.monitoringResponseData.status}" onclick="soundOfResponseStatus(this.value)">Sound</button> </td>
        </tr>
        <tr class="error_description">
            <td colspan="4"> ${nullDescriptionCheck(monitoringRequest.monitoringResponseData.description)}</td>
        </tr>
        <tr class="request_config">
            <td colspan="4"><span class="fw-bold">Monitoring Configuration:</span> Monitoring period 1 time in ${monitoringRequest.monitoringRequestData.monitoringPeriodDelay} ${monitoringRequest.monitoringRequestData.monitoringPeriodTimeUnit.toLowerCase()},
                            Expected response status - ${monitoringRequest.monitoringRequestData.okResponseStatus}
            </td>
        </tr>
    `;
        $('.monitoring_request_table_body').append(markup);
    });
}

function updateMonitoringRequest() {
    initializeMonitoringRequest();
    clearMonitoringRequestTable();
    displayMonitoringRequest();
}

function deleteMonitoringRequest(value) {
    let url = "/api/monitoring/delete/" + value;
    console.log(url);
    httpDelete(url, function () {
        setTimeout(() => { window.location.href = window.location.href.split("#")[0]; }, 1000);
    })
}

function addMonitoringRequest(e) {
    e.preventDefault();
    let url = "/api/monitoring/add";
    let data = {};
    data.monitoringPeriodDelay = $('[name="monitoringPeriodDelay"]').val();
    data.okResponseTime = $('[name="okResponseTime"]').val();
    data.warningResponseTime = $('[name="warningResponseTime"]').val();
    data.okResponseStatus = $('select.okResponseStatus').val();
    data.url = $('[name="url"]').val();
    data.monitoringPeriodTimeUnit = $('select.monitoringPeriodTimeUnit').val()
    data.substringInResponse = $('[name="substringInResponse"]').val();
    data.minResponseSize = $('[name="minResponseSize"]').val();
    data.maxResponseSize = $('[name="maxResponseSize"]').val();
    post(url, data, function () {
        setTimeout(() => { window.location.href = window.location.href.split("#")[0]; }, 1000);
    })
}

async function initializeMonitoringRequest() {
    let url = "/api/monitoring/get/data";
    const data = await syncGet(url);
    data.forEach(bean => {
        monitoringRequest.set(bean.id, bean);
    });
}

function nullDescriptionCheck(description) {
    return description ? description : "<span class='fw-bold'>Description:</span> Everything is OK"
}

function clearMonitoringRequestTable() {
    $('.monitoring_request_table_body').empty();
}

function soundOfResponseStatus(status) {
    if (status === "CRITICAL") {
        playCriticalSound();
    } else if (status === "WARNING") {
        playWarningSound();
    }
}

function playCriticalSound() {
    let sound = document.getElementById("warning_sound");
    sound.play();
}

function playWarningSound() {
    let sound = document.getElementById("warning_sound");
    sound.play();
}