import {FormikValues} from "formik";
import {User} from "../types";

export const saveAuthData = (form: FormikValues) => {
    localStorage.setItem('user', form.name);
    localStorage.setItem('password', form.password);
}

export const authenticate = (user: User) => {
    sessionStorage.setItem('isAuthenticated', 'true');
    localStorage.setItem('user', JSON.stringify(user));
}