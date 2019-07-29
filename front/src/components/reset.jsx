import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage, modalConfirmation } from 'services/modal.jsx';

class ResetUser extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('reset-user', this);

        // Functions binding to this.
        this.reset = this.reset.bind(this);
        this.resetuser = this.resetuser.bind(this);
        this.volver = this.volver.bind(this);
        
        // State.
        this.state = {
            user: '',
            username: '',
            token: '',
            password1: '',
            password2: ''
        };

    }
    
    componentDidMount() {

        this.reset();

    }
    
    reset(){
        const search = this.props.location.search;
        const params = new URLSearchParams(search);
        this.state.token = params.get('token');
        this.state.token == null ? null:
        get({
            url: '/api/user/reset',
            params:{token:this.state.token},
            callback: (response) => {
                this.setState({
                    user: response
                });
                this.setState({
                    username: response.username
                });
                this.setState({
                    userolename: response.role
                });
            }
        });
    }
            
    resetuser() {
        post({
            url: '/api/user/reset',
            body: {
                token: this.state.token,
                newPassword1: this.state.password1,
                newPassword2: this.state.password2,
            },
            callback: (response) => {
                
                if (response.success) {
                    modalMessage(t('reset.txt-reset'), t('reset.txt-reset-user'), () => {
                        changeLocation('/login');
                    });
                  
                } else {
                    modalMessage(t('global.error'), t('login.incorrect-credentials'), () => {
                        
                    });
                }
            }
        });
    }
    
    volver(){
        changeLocation('/login');
    }
    
    render() {

        return (
            <Container>

                <Col className="main-col">
                    <Jumbotron className="text-center">
                        <h1>{t('login.header')}</h1><hr />
                        <h3>{t('reset.head-reset-password')} <strong>{this.state.username}</strong></h3><hr />
                        <Form onSubmit={(e) => { e.preventDefault(); this.resetuser(); }}>
                            <FormGroup>
                                <Input
                                    type="password"
                                    autoComplete="new-password"
                                    placeholder={t('reset.txt-password-1')}
                                    onChange={(e) => { this.setState({ password1: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup>
                                <Input
                                    type="password"
                                    autoComplete="new-password"
                                    placeholder={t('reset.txt-password-2')}
                                    onChange={(e) => { this.setState({ password2: e.target.value }) }}
                                />
                            </FormGroup>
                            <FormGroup className="group-spaced">
                                <Button type="submit" color="primary">{t('reset.btn-reset')}</Button>
                                <Button color="secondary" onClick={this.volver}>{t('reset.btn-return')}</Button>
                            </FormGroup>
                        </Form>
                    </Jumbotron>
                </Col>
            </Container>
        );
    }
}

export default ResetUser;
