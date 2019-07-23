webpackHotUpdate("main",{

/***/ "./src/components/app.jsx":
/*!********************************!*\
  !*** ./src/components/app.jsx ***!
  \********************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react */ \"./node_modules/react/index.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var react_i18next__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react-i18next */ \"./node_modules/react-i18next/dist/es/index.js\");\n/* harmony import */ var react_router_dom__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! react-router-dom */ \"./node_modules/react-router-dom/esm/react-router-dom.js\");\n/* harmony import */ var react_overlay_loader__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! react-overlay-loader */ \"./node_modules/react-overlay-loader/dist/index.esm.js\");\n/* harmony import */ var react_overlay_loader_styles_css__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! react-overlay-loader/styles.css */ \"./node_modules/react-overlay-loader/styles.css\");\n/* harmony import */ var react_overlay_loader_styles_css__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(react_overlay_loader_styles_css__WEBPACK_IMPORTED_MODULE_4__);\n/* harmony import */ var reactstrap__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! reactstrap */ \"./node_modules/reactstrap/es/index.js\");\n/* harmony import */ var services_view_components_jsx__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! services/view-components.jsx */ \"./src/services/view-components.jsx\");\n/* harmony import */ var services_translation_jsx__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! services/translation.jsx */ \"./src/services/translation.jsx\");\n/* harmony import */ var services_location_jsx__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! services/location.jsx */ \"./src/services/location.jsx\");\n/* harmony import */ var services_modal_jsx__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! services/modal.jsx */ \"./src/services/modal.jsx\");\n/* harmony import */ var components_login_jsx__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! components/login.jsx */ \"./src/components/login.jsx\");\n/* harmony import */ var components_navigation_bar_jsx__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! components/navigation-bar.jsx */ \"./src/components/navigation-bar.jsx\");\n/* harmony import */ var components_accounts_jsx__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! components/accounts.jsx */ \"./src/components/accounts.jsx\");\n/* harmony import */ var components_account_jsx__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! components/account.jsx */ \"./src/components/account.jsx\");\n/* harmony import */ var components_profile_jsx__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! components/profile.jsx */ \"./src/components/profile.jsx\");\n/* harmony import */ var components_new_user_jsx__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! components/new-user.jsx */ \"./src/components/new-user.jsx\");\n/* harmony import */ var components_users_jsx__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! components/users.jsx */ \"./src/components/users.jsx\");\nfunction _typeof(obj) { if (typeof Symbol === \"function\" && typeof Symbol.iterator === \"symbol\") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === \"function\" && obj.constructor === Symbol && obj !== Symbol.prototype ? \"symbol\" : typeof obj; }; } return _typeof(obj); }\n\nfunction _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError(\"Cannot call a class as a function\"); } }\n\nfunction _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if (\"value\" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }\n\nfunction _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }\n\nfunction _possibleConstructorReturn(self, call) { if (call && (_typeof(call) === \"object\" || typeof call === \"function\")) { return call; } return _assertThisInitialized(self); }\n\nfunction _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }\n\nfunction _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError(\"this hasn't been initialised - super() hasn't been called\"); } return self; }\n\nfunction _inherits(subClass, superClass) { if (typeof superClass !== \"function\" && superClass !== null) { throw new TypeError(\"Super expression must either be null or a function\"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }\n\nfunction _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nvar App =\n/*#__PURE__*/\nfunction (_Component) {\n  _inherits(App, _Component);\n\n  function App(props) {\n    var _this;\n\n    _classCallCheck(this, App);\n\n    // Props.\n    _this = _possibleConstructorReturn(this, _getPrototypeOf(App).call(this, props)); // Register this view component.\n\n    Object(services_view_components_jsx__WEBPACK_IMPORTED_MODULE_6__[\"registerViewComponent\"])('app', _assertThisInitialized(_this)); // State.\n\n    _this.state = {\n      loading: false,\n      modalMessageActive: false,\n      modalMessageHeader: '',\n      modalMessageBody: '',\n      modalMessageExitCallback: null,\n      modalConfirmationActive: false,\n      modalConfirmationHeader: '',\n      modalConfirmationBody: '',\n      modalConfirmationYesCallback: null,\n      modalConfirmationNoCallback: null\n    };\n    return _this;\n  }\n\n  _createClass(App, [{\n    key: \"componentWillMount\",\n    value: function componentWillMount() {\n      Object(services_location_jsx__WEBPACK_IMPORTED_MODULE_8__[\"listenLocationChange\"])();\n    }\n  }, {\n    key: \"render\",\n    value: function render() {\n      return react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(\"div\", null, react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: \"/login\",\n        component: components_login_jsx__WEBPACK_IMPORTED_MODULE_10__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: '/(accounts|account|profile|users)',\n        component: components_navigation_bar_jsx__WEBPACK_IMPORTED_MODULE_11__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: \"/accounts\",\n        component: components_accounts_jsx__WEBPACK_IMPORTED_MODULE_12__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: \"/account\",\n        component: components_account_jsx__WEBPACK_IMPORTED_MODULE_13__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: \"/profile\",\n        component: components_profile_jsx__WEBPACK_IMPORTED_MODULE_14__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: \"/new-user\",\n        component: components_new_user_jsx__WEBPACK_IMPORTED_MODULE_15__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"Route\"], {\n        exact: true,\n        path: \"/users\",\n        component: components_users_jsx__WEBPACK_IMPORTED_MODULE_16__[\"default\"]\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(react_overlay_loader__WEBPACK_IMPORTED_MODULE_3__[\"Loader\"], {\n        loading: this.state.loading,\n        text: Object(services_translation_jsx__WEBPACK_IMPORTED_MODULE_7__[\"t\"])('app.loading-text'),\n        fullPage: true\n      }), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"Modal\"], {\n        isOpen: this.state.modalMessageActive,\n        toggle: services_modal_jsx__WEBPACK_IMPORTED_MODULE_9__[\"closeModalMessage\"]\n      }, react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"ModalHeader\"], null, this.state.modalMessageHeader), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"ModalBody\"], null, this.state.modalMessageBody)), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"Modal\"], {\n        isOpen: this.state.modalConfirmationActive,\n        toggle: function toggle() {\n          Object(services_modal_jsx__WEBPACK_IMPORTED_MODULE_9__[\"closeModalConfirmation\"])('no');\n        }\n      }, react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"ModalHeader\"], null, this.state.modalConfirmationHeader), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"ModalBody\"], null, this.state.modalConfirmationBody), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"ModalFooter\"], null, react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"Button\"], {\n        color: \"primary\",\n        onClick: function onClick() {\n          Object(services_modal_jsx__WEBPACK_IMPORTED_MODULE_9__[\"closeModalConfirmation\"])('yes');\n        }\n      }, Object(services_translation_jsx__WEBPACK_IMPORTED_MODULE_7__[\"t\"])('global.yes')), react__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(reactstrap__WEBPACK_IMPORTED_MODULE_5__[\"Button\"], {\n        color: \"secondary\",\n        onClick: function onClick() {\n          Object(services_modal_jsx__WEBPACK_IMPORTED_MODULE_9__[\"closeModalConfirmation\"])('no');\n        }\n      }, Object(services_translation_jsx__WEBPACK_IMPORTED_MODULE_7__[\"t\"])('global.no')))));\n    }\n  }]);\n\n  return App;\n}(react__WEBPACK_IMPORTED_MODULE_0__[\"Component\"]);\n\n/* harmony default export */ __webpack_exports__[\"default\"] = (Object(react_i18next__WEBPACK_IMPORTED_MODULE_1__[\"withTranslation\"])()(Object(react_router_dom__WEBPACK_IMPORTED_MODULE_2__[\"withRouter\"])(App)));\n\n//# sourceURL=webpack:///./src/components/app.jsx?");

/***/ })

})