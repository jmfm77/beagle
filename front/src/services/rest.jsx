import { loading as loadingF, notLoading as notLoadingF } from 'services/loading.jsx';
import { t } from 'services/translation.jsx';
import { modalMessage } from 'services/modal.jsx';
import { changeLocation } from 'services/location.jsx';
import { logout } from 'services/session.jsx';

/**
 * REST GET.
 * 
 * @param {object} options The options.
 * @param {string} options.url The URL.
 * @param {object} [options.params] The URL parameters.
 * @param {boolean} [options.loading = true] Set the loading state during the request.
 * @param {boolean} [options.loadingChain = false] Indicates if the loading state should not be deactivated once the request has finished.
 * @param {boolean} [options.loadingChained = false] Indicates if the loading state was already active.
 * @param {function} [options.callback] Invoked once the request has finished. Receives a parameter: {object} responseJson.
 * @param {function} [options.businessLogicExceptionCallback] Invoked once the request has finished in case of business logic exception. Receives a parameter: {object} responseJson.
 */
export function get({
    url,
    params,
    loading = true,
    loadingChain = false,
    loadingChained = false,
    callback,
    businessLogicExceptionCallback
}) {

    if (loading && !loadingChained) loadingF();

    var urlWithParams = url;
    if (params) {
        urlWithParams += '?' + Object.keys(params).map((param) => encodeURIComponent(param) + '=' + encodeURIComponent(params[param])).join('&');
    }

    var headers = {
        'Accept': 'application/json'
    };

    fetch(urlWithParams, {
        method: 'GET',
        headers: headers
    }).then((response) => {
        if (loading && (!loadingChain || response.status < 200 || response.status >= 300)) {
            notLoadingF(() => { processSuccess(callback, businessLogicExceptionCallback, response); });
        } else {
            processSuccess(callback, businessLogicExceptionCallback, response);
        }
    }).catch((error) => {
        if (loading) {
            notLoadingF(() => { processError(error); });
        } else {
            processError(error);
        }
    });

}

/**
 * REST POST.
 * 
 * @param {object} options The options.
 * @param {string} options.url The URL.
 * @param {object} [options.body] The POST body.
 * @param {boolean} [options.loading = true] Set the loading state during the request.
 * @param {boolean} [options.loadingChain = false] Indicates if the loading state should not be deactivated once the request has finished.
 * @param {boolean} [options.loadingChained = false] Indicates if the loading state was already active.
 * @param {function} [options.callback] Invoked once the request has finished. Receives a parameter: {object} responseJson.
 * @param {function} [options.businessLogicExceptionCallback] Invoked once the request has finished in case of business logic exception. Receives a parameter: {object} responseJson.
 */
export function post({
    url,
    body,
    loading = true,
    loadingChain = false,
    loadingChained = false,
    callback,
    businessLogicExceptionCallback
}) {

    if (loading && !loadingChained) loadingF();

    var headers = {
        'Accept': 'application/json'
    };
    if (body) {
        headers['Content-Type'] = 'application/json';
    }

    fetch(url, {
        method: 'POST',
        headers: headers,
        body: body ? JSON.stringify(body) : null
    }).then((response) => {
        if (loading && (!loadingChain || response.status < 200 || response.status >= 300)) {
            notLoadingF(() => { processSuccess(callback, businessLogicExceptionCallback, response); });
        } else {
            processSuccess(callback, businessLogicExceptionCallback, response);
        }
    }).catch((error) => {
        if (loading) {
            notLoadingF(() => { processError(error); });
        } else {
            processError(error);
        }
    });

}

function processSuccess(callback, businessLogicExceptionCallback, response) {

    if (response.status >= 200 && response.status < 300) {

        if (callback) {
            response.json().then(callback);
        }

    } else if (response.status == 500) {

        response.json().then((responseJson) => {
            if (businessLogicExceptionCallback) {
                businessLogicExceptionCallback(responseJson);
            } else {
                modalMessage(t('global.error'), t(responseJson.message ? 'errors.' + responseJson.message : 'global.error-occurred'));
            }
        });

    } else if (response.status == 401 || response.status == 403) {

        //changeLocation('/login');
        logout();

    } else if (response.status == 400){
        response.json().then((responseJson) => {
            if(responseJson.message.toString().indexOf(t('global.validation-error'))!=-1){
                modalMessage(t('global.error'), t('global.validacion-error-message'), null);
            }else{
                processError(responseJson.error);
            }
        });    
    } 
    
    else {

        modalMessage(t('global.error'), t('global.error-occurred'), () => {
            changeLocation('/login');
        });

    }

}

function processError(error) {

    modalMessage(t('global.error'), t('global.error-occurred'), () => {
        changeLocation('/login');
    });

}
