function documentReady(handler) {
    $(document).ready(function () {
        handler.call();
    });
}

function jQueryForEach(array, handler) {
    array.each(handler);
}

function addEventListenerFunction(selector, eventType, listenerFunction) {
    $(selector).on(eventType, listenerFunction);
}

function getAttributeForElement(element, attributeName) {
    return element.attr(attributeName);
}

function setAttributeForElement(element, attributeName, attributeValue) {
    element.attr(attributeName, attributeValue);
}