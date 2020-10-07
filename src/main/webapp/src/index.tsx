import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import {createGlobalStyle} from "styled-components";
import {Routes} from "./router/Routes";
import {BrowserRouter as Router} from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

const GlobalStyles = createGlobalStyle`
    body {
      margin: 0;
      font-size: 1rem;
      font-family: -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', 'Oxygen',
        'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue',
        sans-serif;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
      background-color: #f8f8f8;
    }
    
    * {
      box-sizing: border-box;
    }
`;

ReactDOM.render(
  <React.StrictMode>
      <GlobalStyles />
      <Router>
          <Routes />
      </Router>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
