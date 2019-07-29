import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage } from 'services/modal.jsx';
import { updateSessionInfo, sessionSitekey } from 'services/session.jsx';
import { ReCaptcha } from 'react-recaptcha-google';

class Password extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('remember-password', this);

        // Functions binding to this.
        this.remember = this.remember.bind(this);
        this.volver = this.volver.bind(this);
        this.onLoadRecaptcha = this.onLoadRecaptcha.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
        

        // Refs.
        this.txtUsername = React.createRef();
        this.captchaMachine = React.createRef();
        
        // State.
        this.state = {
            recaptchaSuccessful: false,
            sitekey: '',
            rol: '',
            username: '',
            password: ''
        };
        
        this.state.sitekey = sessionSitekey();
    }

    componentDidMount() {
                if (this.captchaMachine) {
                    this.captchaMachine.reset
                }
    }

    onLoadRecaptcha() {
          if (this.captchaMachine) {
              this.captchaMachine.reset

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
    
    remember() {
         if (this.state.recaptchaSuccessful == true){
            post({
                url: '/api/user/remember-password',
                body: {
                    username: this.state.username
                },
                callback: (response) => {
                       
                    if (response.success) {
                        modalMessage(t('reset.head-remember-password'), t('reset.txt-remember-password'), () => {
                            
                        });
                      
                    } else {
    
                        modalMessage(t('global.error'), t('login.incorrect-credentials'), () => {
                            
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
                        <h3>{t('reset.head-remember-password')}</h3><hr />

                        <Form onSubmit={(e) => { e.preventDefault(); this.remember();}}>
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
                            <Row>
                                <Col/>
                                <Col>
                                    <ReCaptcha
                                    ref={(el) => {this.captchaMachine = el;}}
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
                                <Button type="submit" color="primary">{t('reset.txt-send')}</Button>
                                <Button color="secondary" onClick={this.volver}>{t('user.btn-return')}</Button>
                            </FormGroup>
                        </Form>
                               
                    </Jumbotron>

                                    
               </Col>

            </Container>
        );
     
    }

}

export default Password;
