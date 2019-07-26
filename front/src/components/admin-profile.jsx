import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage, modalConfirmation } from 'services/modal.jsx';
import { updateSessionInfo, sessionUsername, logout } from 'services/session.jsx'

class AdminProfile extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('admin-profile', this);

        // Functions binding to this.
        this.loadUser = this.loadUser.bind(this);
        this.volver = this.volver.bind(this);
        this.modifyUser = this.modifyUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
        
        // State.
        this.state = {
            user: '',
            userId: '',
            username: '',
            adminpassword: '',
            password1: '',
            password2: ''
        };

    }
    
    componentDidMount() {

        this.loadUser();

    }
    
    loadUser() {
        const search = this.props.location.search;
        const params = new URLSearchParams(search);
        this.state.userId = params.get('userId');
        this.state.userId == null ? null:
        get({
            url: '/api/user/get-user',
            params:{userId:this.state.userId},
            callback: (response) => {
                this.setState({
                    user: response
                });
                this.state.username = this.state.user.username;
            }
        });
        
    }
            
    volver(){
        changeLocation('/users');
    }

    modifyUser() {
        post({
            url: '/api/user/admin-modify',
            body: {
                userId:  this.state.userId,
                adminPassword: this.state.adminpassword,
                newPassword1: this.state.password1,
                newPassword2: this.state.password2,
            },
            callback: (response) => {
                    changeLocation('/users');
            }
        });
    }
    
    deleteUser() {
        modalConfirmation(
                t('global.confirmation'),
                t('profile.delete-user-confirmation'),
                () => {
                    get({
                        url: '/api/user/delete',
                        body: {},
                        callback: (response) => {
                            if (response){ 
                                logout();
                            }
                        }
                    });
                }
            );        
    }
    
    render() {

        return (
            <Container>

                <Col className="main-col">
                    <Jumbotron className="text-center">
                        <h3>{t('profile.header_modify')} {this.state.username}</h3><hr />
                        <Form onSubmit={(e) => { e.preventDefault(); this.modifyUser(); }}>
                            <FormGroup>
                                <Input
                                    type="password"
                                    placeholder={t('profile.txt-admin-password')}
                                    onChange={(e) => { this.setState({ adminpassword: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="password"
                                    placeholder={t('profile.txt-password-1')}
                                    onChange={(e) => { this.setState({ password1: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="password"
                                    placeholder={t('profile.txt-password-2')}
                                    onChange={(e) => { this.setState({ password2: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup className="group-spaced">
                                <Button type="submit" color="primary">{t('profile.btn-modify-user')}</Button>
                                <Button color="secondary" onClick={this.volver}>{t('profile.btn-return')}</Button>
                                <Button color="warning" onClick={this.deleteUser}>{t('profile.btn-delete-user')}</Button>
                            </FormGroup>
                        </Form>
                    </Jumbotron>
                </Col>
            </Container>
        );
    }
}

export default AdminProfile;
