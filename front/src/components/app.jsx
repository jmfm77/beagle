import React, { Component } from 'react';
import { withTranslation } from 'react-i18next';
import { withRouter, Route, NavLink } from 'react-router-dom';
import { Loader } from 'react-overlay-loader';
import reactOverlayLoaderCss from 'react-overlay-loader/styles.css';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { listenLocationChange } from 'services/location.jsx';
import { closeModalMessage, closeModalConfirmation } from 'services/modal.jsx';
import Login from 'components/login.jsx';
import NavigationBar from 'components/navigation-bar.jsx';
import Accounts from 'components/accounts.jsx';
import Account from 'components/account.jsx';
import AdminProfile from 'components/admin-profile.jsx';
import Profile from 'components/profile.jsx';
import User from 'components/new-user.jsx';
import AdminUser from 'components/admin-new-user.jsx';
import Users from 'components/users.jsx';
import ResetUser from 'components/reset.jsx';
import Password from 'components/remember-password.jsx';
import { loadReCaptcha } from 'react-recaptcha-google';

class App extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('app', this);

        // State.
        this.state = {
            loading: false,
            modalMessageActive: false,
            modalMessageHeader: '',
            modalMessageBody: '',
            modalMessageExitCallback: null,
            modalConfirmationActive: false,
            modalConfirmationHeader: '',
            modalConfirmationBody: '',
            modalConfirmationYesCallback: null,
            modalConfirmationNoCallback: null
        }

    }

    componentWillMount() {

        listenLocationChange();

    }

    componentDidMount() {
        loadReCaptcha();
    }
    
    render() {

        return (
            <div>

                <Route exact path='/login' component={Login} />
                <Route exact path={'/(accounts|account|profile|users|admin-new-user|admin-profile)'} component={NavigationBar} />
                <Route exact path='/accounts' component={Accounts} />
                <Route exact path='/account' component={Account} />
                <Route exact path='/admin-profile' component={AdminProfile} />
                <Route exact path='/profile' component={Profile} />
                <Route exact path='/new-user' component={User} />
                <Route exact path='/admin-new-user' component={AdminUser} />
                <Route exact path='/users' component={Users} />
                <Route exact path='/reset' component={ResetUser} />
                <Route exact path='/remember-password' component={Password} />
                
                <Loader loading={this.state.loading} text={t('app.loading-text')} fullPage />

                <Modal isOpen={this.state.modalMessageActive} toggle={closeModalMessage}>
                    <ModalHeader>{this.state.modalMessageHeader}</ModalHeader>
                    <ModalBody>{this.state.modalMessageBody}</ModalBody>
                </Modal>

                <Modal isOpen={this.state.modalConfirmationActive} toggle={() => { closeModalConfirmation('no') }}>
                    <ModalHeader>{this.state.modalConfirmationHeader}</ModalHeader>
                    <ModalBody>{this.state.modalConfirmationBody}</ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={() => { closeModalConfirmation('yes') }}>{t('global.yes')}</Button>
                        <Button color="secondary" onClick={() => { closeModalConfirmation('no') }}>{t('global.no')}</Button>
                    </ModalFooter>
                </Modal>

            </div>
        );

    }

}

export default withTranslation()(withRouter(App));
