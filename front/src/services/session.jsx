import { getViewComponent } from 'services/view-components.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation, currentLocationPath } from 'services/location.jsx';

var currentSession = null;

/**
 * Updates the current session info.
 * 
 * @param {object} options The options.
 * @param {boolean} [options.loading = true] Set the loading state while the session info is being updated.
 * @param {boolean} [options.loadingChain = false] Indicates if the loading state should not be deactivated once the session info has been updated.
 * @param {boolean} [options.loadingChained = false] Indicates if the loading state was already active.
 * @param {function} [options.callback] Invoked once the session info has been updated.
 */
export function updateSessionInfo({
    loading = true,
    loadingChain = false,
    loadingChained = false,
    callback
}) {

    get({
        url: '/api/session/info',
        loading: loading,
        loadingChain: loadingChain,
        loadingChained: loadingChained,
        callback: (response) => {
            
            var previousSession = currentSession || {};
            currentSession = response;
            
            if (currentLocationPath() != '/login' && currentLocationPath() != '/new-user' && currentLocationPath() != '/reset' 
                && currentLocationPath() != '/remember-password' && !isAuthenticated()) {
                changeLocation('/login');
                return;
            }

            if (callback) callback();

        }
    });

}

/**
 * @returns {boolean} Indicates if the current session is authenticated.
 */
export function isAuthenticated() {

    return currentSession && currentSession.authenticated;

}

/**
 * @returns {string} Indicates the current session username, in case it is authenticated, or null otherwise.
 */
export function sessionUsername() {

    return currentSession ? currentSession.username : null;

}

/**
 * @returns {string[]} Indicates the current session roles, in case it is authenticated, or null otherwise.
 */
export function sessionRoles() {

    return currentSession ? currentSession.roles[0]: null;

}

/**
 * Terminates the current session.
 */
export function logout() {

    post({
        url: '/api/session/logout',
        callback: (response) => {

            changeLocation('/login');

        }
    });

}

/**
 * @returns {string} Indicates the current session sitekey.
 */
export function sessionSitekey() {

    return currentSession ? currentSession.sitekey : null;

}
