import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage } from 'services/modal.jsx';

class User extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('user', this);

        // Functions binding to this.
        this.createUser = this.createUser.bind(this);
        this.volver = this.volver.bind(this);
        
        // State.
        this.state = {
            username: '',
            password1: '',
            password2: '',
            role: ''
        };

    }
    
          
    volver(){
        changeLocation('/login');
    }

    createUser() {
        post({
            url: '/api/user/create',
            body: {
                username: this.state.username,
                password1: this.state.password1,
                password2: this.state.password2,
                role: 'ROLE_USER'
            },
            callback: (response) => {
                if (response) {
                    modalMessage(t('user.header-user-created'), t('user.user-created'), () => {
                        changeLocation('/login');
                    });
                } 
            }
        });
    }
    
    render() {

        return (
            <Container>

                <Col className="main-col">
                    <Jumbotron className="text-center">
                        <h3>{t('user.new-user')}</h3><hr />
                        <Form onSubmit={(e) => { e.preventDefault(); this.createUser(); }}>
                            <FormGroup>
                                <Input
                                    type="text"
                                    required
                                    autoFocus
                                    autoComplete="email"
                                    placeholder={t('user.txt-username')}
                                    onChange={(e) => { this.setState({ username: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="password"
                                    autoComplete="current-password"
                                    required
                                    placeholder={t('user.txt-password-1')}
                                    onChange={(e) => { this.setState({ password1: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="password"
                                    autoComplete="current-password"
                                    required
                                    placeholder={t('user.txt-password-2')}
                                    onChange={(e) => { this.setState({ password2: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup className="group-spaced">
                                <Button type="submit" color="primary">{t('user.btn-create-user')}</Button>
                                <Button color="secondary" onClick={this.volver}>{t('user.btn-return')}</Button>
                            </FormGroup>
                        </Form>
                    </Jumbotron>
                </Col>
            </Container>
        );
    }
}

export default User;
