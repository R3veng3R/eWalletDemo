import React from "react";
import {Redirect, Route, RouteProps, Switch} from "react-router-dom";
import {Login} from "../pages/Login";
import {HomePage} from "../pages/Home";

const ProtectedRoute: React.FC<RouteProps> = props => {
    const isAuthenticated = sessionStorage.getItem("isAuthenticated");

    return isAuthenticated
        ? <Route {...props} />
        : <Redirect to="/login" />
};

export const Routes: React.FC = () => {
    return (
        <Switch>
            <ProtectedRoute exact path="/" component={HomePage}/>
            <Route exact path="/login" component={Login}/>
        </Switch>
    );
};