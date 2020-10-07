import {FormikValues} from "formik";
import {User} from "../types";

export abstract class Auth {
    static saveData = (form: FormikValues) => {
        localStorage.setItem('username', form.name);
        localStorage.setItem('password', form.password);
    }

    static authenticate = (user: User) => {
        sessionStorage.setItem('isAuthenticated', 'true');
        localStorage.setItem('user', JSON.stringify(user));
    }

    static getCurrentUser = (): User | null => {
        const json = localStorage.getItem("user");

        if (!json || !json.length) {
            return null;
        } else {
            return JSON.parse(json);
        }
    }
}