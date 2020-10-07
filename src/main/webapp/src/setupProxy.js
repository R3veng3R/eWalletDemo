const { createProxyMiddleware } = require('http-proxy-middleware');

const API_HOST = process.env.REACT_APP_API_HOST ? process.env.REACT_APP_API_HOST : 'localhost'

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: `http://${API_HOST}:8080`,
            changeOrigin: true,
        })
    );
};