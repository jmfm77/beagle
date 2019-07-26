import React, { Component } from 'react';
import { Navbar, NavbarBrand, NavbarToggler, Collapse, Nav, NavItem, NavLink, Button } from 'reactstrap';
import { NavLink as RRNavLink } from "react-router-dom"
import { registerViewComponent, getViewComponent } from 'services/view-components.jsx';
import { t } from 'services/translation.jsx';
import { updateSessionInfo, sessionUsername, sessionRoles, logout } from 'services/session.jsx';

class NavigationBar extends Component {

    constructor(props) {

        // Props.
        super(props);

        // Register this view component.
        registerViewComponent('navigationBar', this);

        // Functions binding to this.
        this.toggleNavbarToggler = this.toggleNavbarToggler.bind(this);

        // State.
        this.state = {
            navbarTogglerActive: false,
            username: null,
            rol: null
        };

    }
    
    componentDidMount() {

        updateSessionInfo({
            callback: () => {
                this.setState({
                    username: sessionUsername()
                });
                this.setState({
                    rol: sessionRoles()
                });
            }
        });
    }

    toggleNavbarToggler() {

        this.setState({
            navbarTogglerActive: !this.state.navbarTogglerActive
        });

    }

    render() {

        return (
            <div>
                <Navbar color="light" light expand="md" style={{ marginBottom: "3rem" }}>

                    <NavbarBrand>{t('navigation-bar.brand')}</NavbarBrand>
                    <NavbarToggler onClick={this.toggleNavbarToggler} />

                    <Collapse isOpen={this.state.navbarTogglerActive} navbar>

                        {this.state.rol != null?
                            this.state.rol == t('global.role-admin')?
                                <Nav className="mr-auto" navbar>
                                    <NavItem><NavLink tag={RRNavLink} to={'/users'} activeClassName="active">{t('navigation-bar.items')}</NavLink></NavItem>
                                </Nav>:
                                    this.state.rol == t('global.role-user')?
                                        <Nav className="mr-auto" navbar>
                                            <NavItem><NavLink tag={RRNavLink} to={'/accounts'} activeClassName="active">{t('navigation-bar.items')}</NavLink></NavItem>
                                        </Nav>:null:null
                        }
                            
                        <Nav className="ml-auto" navbar>
                            <NavItem><NavLink tag={RRNavLink} to={'/profile'} activeClassName="active">{this.state.username}</NavLink></NavItem>
                            <div className="form-inline"><Button color="secondary" size="sm" onClick={logout}>{t('navigation-bar.logout')}</Button></div>
                        </Nav>

                    </Collapse>

                </Navbar>
            </div>
        );

    }

}

export default NavigationBar;
