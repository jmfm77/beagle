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
import Profile from 'components/profile.jsx';

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

    render() {

        return (
            <div>

                <Route exact path='/login' component={Login} />
                <Route exact path={'/(accounts|account)'} component={NavigationBar} />
                <Route exact path='/accounts' component={Accounts} />
                <Route exact path='/account' component={Account} />
                
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
