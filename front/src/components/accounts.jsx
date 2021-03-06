import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Container, Button, Table, Form, FormGroup, Input, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import Octicon, { DiffAdded, Sync, Trashcan, RepoPull, Organization } from '@githubprimer/octicons-react'
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { modalConfirmation } from 'services/modal.jsx';
import { changeLocation } from 'services/location.jsx';
import { CopyToClipboard } from 'react-copy-to-clipboard';

class Accounts extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('accounts', this);

        // Functions binding to this.
        this.loadAccounts = this.loadAccounts.bind(this);
        this.ceateAccount = this.createAccount.bind(this);
        this.deleteAccount = this.deleteAccount.bind(this);

        // State.
        this.state = {
            accounts: [],
            accountModalActive: false,
            accountName: '',
            accountDescription: '',
            accountPassword: '',
            copied: false
        };

    }

    componentDidMount() {

        this.loadAccounts();
    }
    
    loadAccounts() {

        get({
            url: '/api/secured-accounts/get-all-my-accounts',
            callback: (response) => {
                this.setState({
                    accounts: response
                });
            }
        });

    }

    deleteAccount(account) {

        modalConfirmation(
            t('global.confirmation'),
            t('accounts.delete-account-confirmation'),
            () => {

                post({
                    url: '/api/secured-accounts/delete',
                    body: {
                        'name': account.name
                    },
                    callback: (response) => {
                        this.loadAccounts();
                    }
                });

            }
        );

    }
 
    createAccount() {

        changeLocation('/account');
    }

    render() {

        return (
            <Container>

                <h4>{t('accounts.header')}</h4><hr />

                <div className="group-spaced" style={{ margin: '1.5rem 0' }}>
                    <Button color="primary" onClick={() => { this.createAccount() }}>{t('accounts.btn-create-account')}</Button>
                </div>

                {
                    this.state.accounts == null ?
                        null :
                        this.state.accounts.length == 0 ?
                            <p>{t('accounts.no-accounts')}</p> :
                            <Table striped hover>
                                <thead>
                                    <tr>
                                        <th style={{ width: '40%' }}>{t('accounts.table-name')}</th>
                                        <th style={{ width: '60%' }}>{t('accounts.table-description')}</th>
                                        {/*<th style={{ width: '30%' }}>{t('accounts.table-uri')}</th>
                                        <th style={{ width: '10%' }}>{t('accounts.table-user')}</th>
                                        <th style={{ width: '10%' }}>{t('accounts.table-password')}</th>
                                        <th style={{ width: '10%' }}></th>*/}
                                        <th style={{ width: '5rem' }}></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {this.state.accounts.map((account, i) =>
                                        <tr key={'secret-' + account.nombre}>
                                            <td style={{ width: '40%' }}><Link to={'/account?securedAccountId='+account.securedAccountId}>{account.name}</Link></td>
                                            <td style={{ width: '60%' }}>{account.description}</td>
                                            {/*<td style={{ width: '30%' }}>{account.uri}</td>
                                            <td style={{ width: '10%' }}>{account.username}</td>
                                            <td style={{ width: '10%' }}>
                                           
                                                <Input 
                                                    type="password"
                                                    disabled
                                                    value={account.password}
                                                /> 
                                            </td>
                                            
                                            <td style={{ width: '10%' }}>
                                                <CopyToClipboard text={account.uri} 
                                                    >
                                                    <Button color="warning" size="sm">{t('accounts.table-uri')}</Button>
                                                </CopyToClipboard>
                                               
                                            </td>
                                            */}
                                            <td style={{ width: '5rem' }} align="center">
                                                <span onClick={() => { this.deleteAccount(account) }} style={{ cursor: 'pointer' }}>
                                                    <Octicon icon={Trashcan} />
                                                </span>
                                                <span className="space-between-icons"></span>
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                }
            </Container>
        );

    }

}

export default Accounts;
