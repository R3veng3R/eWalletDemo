import React, {useEffect, useState} from "react";
import {LoginForm} from "../components/LoginForm";
import {FormikValues} from "formik";
import styled from "styled-components";
import {Redirect, RouteComponentProps} from "react-router";
import {Auth} from "../utils/Auth";
import {Api} from "../utils/Api";
import {User} from "../types";

const Container = styled.div`
  max-width: 30%;
  margin: 15% auto;
`;

export const Login: React.FC<RouteComponentProps> = () => {
    const [isLoggedIn, setLoggedIn] = useState<boolean>(false);

    const login = async (formValues: FormikValues) => {
        Auth.saveData(formValues);
        const result = await Api.get("/api/user");

        if (result.status === 200) {
            const user: User = result.data;
            Auth.authenticate(user);
            setLoggedIn(true);
        } else {
        }
    }

    return (
        <Container>
            <LoginForm onSubmit={ login } />
            { isLoggedIn ? <Redirect to="/" /> : null }
        </Container>
    );
}