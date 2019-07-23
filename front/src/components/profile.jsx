import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage, modalConfirmation } from 'services/modal.jsx';
import { updateSessionInfo, sessionUsername, logout } from 'services/session.jsx'

class Profile extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('profile', this);

        // Functions binding to this.
        this.modifyUser = this.modifyUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);
        
        // State.
        this.state = {
            profileModalActive: false,
            username: '',
            oldpassword: '',
            password1: '',
            password2: ''
        };

    }
    
    componentDidMount() {

        updateSessionInfo({
            callback: () => {
                this.setState({
                    username: sessionUsername()
                });
            }
        });

    }
            
    volver(){
        changeLocation('/accounts');
    }

    modifyUser() {
        post({
            url: '/api/user/modify',
            body: {
                oldPassword: this.state.oldpassword,
                newPassword1: this.state.password1,
                newPassword2: this.state.password2,
            },
            callback: (response) => {
                    changeLocation('/accounts');
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
                                    placeholder={t('profile.txt-old-password')}
                                    onChange={(e) => { this.setState({ oldpassword: e.target.value }) }}
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

export default Profile;
