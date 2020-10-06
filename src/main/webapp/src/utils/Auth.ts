import {FormikValues} from "formik";

export const authenticate = (form: FormikValues) => {
    localStorage.setItem('user', form.name);
    localStorage.setItem('password', form.password);
    sessionStorage.setItem('isAuthenticated', 'true');
}