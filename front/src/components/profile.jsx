import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage } from 'services/modal.jsx';
import { updateSessionInfo, sessionUsername, logout } from 'services/session.jsx'

class Profile extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('profile', this);

        // Functions binding to this.
        this.modifyUsert = this.modifyUsert.bind(this);
        
        // State.
        this.state = {
            username: sessionUsername(),
            oldpassword: '',
            password1: '',
            password2: ''
        };

    }
    
    componentDidMount() {

        this.loadAccount();

    }
        
    volver(){
        changeLocation('/accounts');
    }

    modifyUsert() {
        post({
            url: '/api/secured-accounts/modify-user',
            body: {
                oldpassword: this.state.user.oldpassword,
                password: this.state.user.password
            },
            callback: (response) => {
                    changeLocation('/accounts');
            }
        });
    }
    
    render() {

        return (
            <Container>

                <Col className="main-col">
                    <Jumbotron className="text-center">
                        <h3>{t('profile.header_modify')}</h3><hr />
                        <Form onSubmit={(e) => { e.preventDefault(); this.modifyUsert(); }}>
                            <FormGroup> 
                                <Label>{t('profile.txt-username')}</Label>
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="text"
                                    placeholder={t('profile.txt-old-password')}
                                    onChange={(e) => { this.setState({ oldpassword: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="text"
                                    placeholder={t('profile.txt-password-1')}
                                    onChange={(e) => { this.setState({ password1: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="text"
                                    placeholder={t('profile.txt-password-2')}
                                    onChange={(e) => { this.setState({ password2: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup className="group-spaced">
                                <Button type="submit" color="primary">{t('profile.btn-modify-user')}</Button>
                                <Button color="secondary" onClick={this.volver}>{t('profile.btn-return')}</Button>
                            </FormGroup>
                        </Form>
                    </Jumbotron>
                </Col>
            </Container>
        );
    }
}

export default Profile;
