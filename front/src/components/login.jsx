import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage } from 'services/modal.jsx';
import { updateSessionInfo, sessionRoles, sessionUsername } from 'services/session.jsx';

class Login extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('login', this);

        // Functions binding to this.
        this.login = this.login.bind(this);
        this.whereIGo = this.whereIGo.bind(this);

        // Refs.
        this.txtUsername = React.createRef();
        
        // State.
        this.state = {
            rol: '',
            username: '',
            password: ''
        };

    }

    whereIGo() {
        this.state.rol = sessionRoles();
        if (this.state.rol != null){
            this.state.rol == t('global.role-admin')?
                    changeLocation('/users'):
                        this.state.rol == t('global.role-user')?
                                changeLocation('/accounts'):
                                    modalMessage(t('global.error'), t('login.incorrect-credentials'), () => {
                                        this.txtUsername.current.focus();
                                    });
        }else{
            modalMessage(t('global.error'), t('login.incorrect-credentials'), () => {
                this.txtUsername.current.focus();
            }); 
        }
    }
    
    login() {

        post({
            url: '/api/session/login',
            body: {
                username: this.state.username,
                password: this.state.password
            },
            callback: (response) => {
                   
                if (response.success) {
                    updateSessionInfo({
                        callback: () => {
                            this.whereIGo();
                        }                        
                    });
                  
                } else {

                    modalMessage(t('global.error'), t('login.incorrect-credentials'), () => {
                        this.txtUsername.current.focus();
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
                        <h1>{t('login.header')}</h1><hr />
                        <h3>{t('login.header_1')}</h3><hr />

                        <Form onSubmit={(e) => { e.preventDefault(); this.login(); }}>
                            <FormGroup>
                                <Input
                                    innerRef={this.txtUsername}
                                    type="text"
                                    autoComplete="email" 
                                    placeholder={t('login.txt-username')}
                                    required
                                    autoFocus
                                    onChange={(e) => { this.setState({ username: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="password"
                                    autoComplete="current-password"
                                    placeholder={t('login.txt-password')}
                                    required
                                    onChange={(e) => { this.setState({ password: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup className="group-spaced">
                                <Button type="submit" color="primary">{t('login.btn-login')}</Button>
                            </FormGroup>
                            <FormGroup>
                                <Link to={'/new-user'}>{t('user.new-user')}</Link>
                            </FormGroup>
                                <FormGroup>
                                <Link to={'/remember-password'}>{t('login.link-remember-password')}</Link>
                            </FormGroup>
                            
                        </Form>
                               
                    </Jumbotron>

                                    
               </Col>

            </Container>
        );

    }

}

export default Login;
