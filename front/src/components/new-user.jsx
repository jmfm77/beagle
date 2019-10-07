import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage } from 'services/modal.jsx';
import { updateSessionInfo, sessionSitekey } from 'services/session.jsx';
import { ReCaptcha } from 'react-recaptcha-google';

class User extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('user', this);

        // Functions binding to this.
        this.createUser = this.createUser.bind(this);
        this.volver = this.volver.bind(this);
        this.onLoadRecaptcha = this.onLoadRecaptcha.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
        
        // Refs.
        this.captchaBeagle = React.createRef();
        
        // State.
        this.state = {
            recaptchaSuccessful: false,
            sitekey: '',
            username: '',
            password1: '',
            password2: '',
            role: ''
        };
        
        this.state.sitekey = sessionSitekey();
    }
    
    componentDidMount() {
        if (this.captchaBeagle) {
            this.captchaBeagle.reset
        }
      }
      onLoadRecaptcha() {
          if (this.captchaBeagle) {
              this.captchaBeagle.reset
          }
      }
      verifyCallback(recaptchaToken) {
        // Here you will get the final recaptchaToken!!! 
        if(recaptchaToken != null && recaptchaToken.length){
            //console.log(recaptchaToken, "<= your recaptcha token")
            this.setState({recaptchaSuccessful: true})
        }else {
            this.setState({recaptchaSuccessful: false})
            modalMessage(t('global.error'), t('error.captcha-error'), () => {
                
            });
    
        }
        
      }  
    

    volver(){
        changeLocation('/login');
    }

    createUser() {
        if (this.state.recaptchaSuccessful==true){
            post({
                url: '/api/user/create',
                body: {
                    username: this.state.username,
                    password1: this.state.password1,
                    password2: this.state.password2,
                    role: t('global.role-user')
                },
                callback: (response) => {
                    if (response) {
                        modalMessage(t('user.header-user-created'), t('user.user-created'), () => {
                            changeLocation('/login');
                        });
                    } 
                }
            });
        }else {
            
            modalMessage(t('global.error'), t('errors.captcha-error'), () => {
                
            });

        }
    }
    
    render() {

        return (
            <Container>

                <Col className="main-col">
                    <Jumbotron className="text-center">
                        <h1>{t('login.header')}</h1><hr />
                        <h3>{t('user.new-user')}</h3><hr />
                        <Form onSubmit={(e) => { e.preventDefault(); this.createUser();}}>
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
                            <Row>
                                <Col/>
                                <Col>
                                    <ReCaptcha
                                    ref={(el) => {this.captchaBeagle = el;}}
                                    size="normal"
                                    render="explicit"
                                    sitekey={this.state.sitekey}
                                    onloadCallback={this.onLoadRecaptcha}
                                    verifyCallback={this.verifyCallback}
                                    hl="es"
                                    />
                                </Col>
                                <Col/>
                            </Row>
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
