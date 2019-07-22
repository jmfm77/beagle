import React, { Component } from 'react';
import { Container, Row, Col, Jumbotron, Form, FormGroup, Label, Input, InputGroup, Button, Popover, PopoverBody, Modal, ModalHeader, ModalBody } from 'reactstrap';
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { get, post } from 'services/rest.jsx';
import { changeLocation } from 'services/location.jsx';
import { modalMessage } from 'services/modal.jsx';

class Account extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('account', this);

        // Functions binding to this.
        this.loadAccount = this.loadAccount.bind(this);
        this.return = this.return.bind(this);
        this.createAccount = this.createAccount.bind(this);
        this.modifyAccount = this.modifyAccount.bind(this);

        // State.
        this.state = {
            new: false,
            account: '',
            name: '',
            description: '',
            uri: '',
            username: '',
            password: ''
        };

    }
    
    componentDidMount() {

        this.loadAccount();

    }

    loadAccount() {
        const search = this.props.location.search;
        const params = new URLSearchParams(search);
        const securedAccountId = params.get('securedAccountId');
        securedAccountId == null ?
        this.state.new = true:
        get({
            url: '/api/secured-accounts/get-my-account',
            params:{securedAccountId:securedAccountId},
            callback: (response) => {
                this.setState({
                    account: response
                });
                this.state.name = this.state.account.name;
                this.state.description = this.state.account.description; 
                this.state.uri = this.state.account.uri;
                this.state.username = this.state.account.username; 
                this.state.password =this.state.account.password; 
            }
        });
        
    }
    
    return(){
        changeLocation('/accounts');
    }

    createAccount() {

        post({
            url: '/api/secured-accounts/create',
            body: {
                name: this.state.name,
                description: this.state.description,
                uri: this.state.uri,
                username: this.state.username,
                password: this.state.password
            },
            callback: (response) => {
                    changeLocation('/accounts');
            }
        });

    }
    
    modifyAccount() {

        post({
            url: '/api/secured-accounts/modify',
            body: {
                securedAccountId: this.state.account.securedAccountId,
                name: this.state.account.name,
                description: this.state.description,
                uri: this.state.uri,
                username: this.state.username,
                password: this.state.password
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
                    {
                        this.state.new == true ?
                        <Jumbotron className="text-center">
                            <h3>{t('account.header_new')}</h3><hr />
                            <Form onSubmit={(e) => { e.preventDefault(); this.createAccount(); }}>
                                <FormGroup>
                                    <Input
                                        innerRef={this.txtName}
                                        type="text"
                                        placeholder={t('account.txt-name')}
                                        required
                                        autoFocus
                                        onChange={(e) => { this.setState({ name: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        type="text"
                                        placeholder={t('account.txt-description')}
                                        onChange={(e) => { this.setState({ description: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        type="text"
                                        placeholder={t('account.txt-uri')}
                                        onChange={(e) => { this.setState({ uri: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        type="text"
                                        placeholder={t('account.txt-user')}
                                        required
                                        onChange={(e) => { this.setState({ username: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup>
                                    <Input
                                        type="password"
                                        placeholder={t('account.txt-password')}
                                        required
                                        onChange={(e) => { this.setState({ password: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup className="group-spaced">
                                    <Button type="submit" color="primary">{t('account.btn-create-account')}</Button>
                                    <Button color="secondary" onClick={this.return}>{t('account.btn-volver-account')}</Button>
                                </FormGroup>
                            </Form>
                        </Jumbotron>:
                        this.state.account == null ?
                        <p>{t('account.no-account')}</p> :
                        <Jumbotron className="text-center">
                            <h3>{t('account.header_modify')} {this.state.account.name}</h3><hr />
                            <Form onSubmit={(e) => { e.preventDefault(); this.modifyAccount(); }}>
                                <FormGroup row>
                                    <Label>{t('account.txt-description')}</Label>
                                    <Input
                                        type="text"
                                        defaultValue={this.state.account.description}
                                        placeholder={t('account.txt-description')}
                                        onChange={(e) => { this.setState({ description: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup row>
                                    <Label>{t('account.txt-uri')}</Label>
                                    <Input
                                        type="text"
                                        defaultValue={this.state.account.uri}
                                        placeholder={t('account.txt-uri')}
                                        onChange={(e) => { this.setState({ uri: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup row>
                                    <Label>{t('account.txt-user')}</Label>
                                    <Input
                                        type="text"
                                        defaultValue={this.state.account.username}
                                        placeholder={t('account.txt-user')}
                                        required
                                        onChange={(e) => { this.setState({ username: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup row>
                                    <Label>{t('account.txt-password')}</Label>
                                    <Input
                                        type="text"
                                        defaultValue={this.state.account.password}
                                        placeholder={t('account.txt-password')}
                                        required
                                        onChange={(e) => { this.setState({ password: e.target.value }) }}
                                    />
                                </FormGroup>
                                <FormGroup className="group-spaced">
                                    <Button type="submit" color="primary">{t('account.btn-modify-account')}</Button>
                                    <Button color="secondary" onClick={this.volver}>{t('account.btn-volver-account')}</Button>
                                </FormGroup>
                            </Form>
                        </Jumbotron>
                    }
                </Col>

            </Container>
        );
    }
}

export default Account;
