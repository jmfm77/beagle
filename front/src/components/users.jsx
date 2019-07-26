import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Container, Button, Table, Form, FormGroup, Input, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import Octicon, { DiffAdded, Sync, Trashcan, RepoPull, Organization } from '@githubprimer/octicons-react'
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { modalConfirmation } from 'services/modal.jsx';
import { changeLocation } from 'services/location.jsx';

class Users extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('users', this);

        // Functions binding to this.
        this.loadUsers = this.loadUsers.bind(this);
        this.ceateUser = this.createUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);

        // State.
        this.state = {
            users: [],
            accountModalActive: false,
            userName: '',
            role: ''
        };

    }

    componentDidMount() {

        this.loadUsers();

    }

    loadUsers() {

        get({
            url: '/api/user/get-all-users',
            callback: (response) => {
                this.setState({
                    users: response
                });
            }
        });

    }

    deleteUser(user) {

        modalConfirmation(
            t('global.confirmation'),
            t('users.delete-user-confirmation'),
            () => {

                post({
                    url: '/api/user/admin-delete',
                    body: {
                        'userId': user.userId
                    },
                    callback: (response) => {
                        this.loadUsers();
                    }
                });

            }
        );

    }
 
    createUser() {

        changeLocation('/admin-new-user');
    }

    render() {

        return (
            <Container>

                <h4>{t('users.header')}</h4><hr />

                <div className="group-spaced" style={{ margin: '1.5rem 0' }}>
                    <Button color="primary" onClick={() => { this.createUser() }}>{t('users.btn-create-user')}</Button>
                </div>

                {
                    this.state.users == null ?
                        null :
                        this.state.users.length == 0 ?
                            <p>{t('users.no-users')}</p> :
                            <Table striped hover>
                                <thead>
                                    <tr>
                                        <th style={{ width: '50%' }}>{t('users.table-name')}</th>
                                        <th style={{ width: '50%' }}>{t('users.table-rol')}</th>
                                        <th style={{ width: '5rem' }}></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {this.state.users.map((user, i) =>
                                        <tr key={'secret-' + user.username}>
                                            <td style={{ width: '50%' }}><Link to={'/admin-profile?userId='+user.userId}>{user.username}</Link></td>
                                            <td style={{ width: '50%' }}>{user.role}</td>
                                            <td style={{ width: '5rem' }} align="center">
                                                <span onClick={() => { this.deleteUser(user) }} style={{ cursor: 'pointer' }}>
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

export default Users;
