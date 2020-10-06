import React, {useEffect, useState} from "react";
import {LoginForm} from "../components/LoginForm";
import {FormikValues} from "formik";
import styled from "styled-components";
import {Redirect, RouteComponentProps, withRouter} from "react-router";
import {authenticate} from "../utils/Auth";

const Container = styled.div`
  max-width: 30%;
  margin: 15% auto;
`;

export const Login: React.FC<RouteComponentProps> = () => {
    const [isLoggedIn, setLoggedIn] = useState<boolean>(false);

    const logIn = async (formValues: FormikValues) => {
        authenticate(formValues);
        setLoggedIn(true);
    }

    return (
        <Container>
            <LoginForm onSubmit={ logIn } />
            {isLoggedIn ? <Redirect to="/" /> : null}
        </Container>
    );
}