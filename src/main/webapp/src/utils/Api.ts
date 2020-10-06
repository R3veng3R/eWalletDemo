import axios from 'axios';
const BASE_URL = process.env.NODE_ENV === 'production' ? '/' : '/backend'

export const Api = axios.create({
    baseURL: BASE_URL
});

Api.interceptors.request.use(config => {
        debugger;
        const username = localStorage.getItem("user");
        const password = localStorage.getItem("password");

        if (username && password) {
            config.auth = {
                username,
                password
            }
        }

        return config;
    },
    error => Promise.reject(error)
);