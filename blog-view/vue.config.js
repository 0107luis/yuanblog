const webpack = require("webpack");
module.exports = {
    publicPath: '/',
    devServer: {
        port: 8080,
        historyApiFallback: true,
        proxy: {
            '/types': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/blog': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/tags': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/friend': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/comment': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/user': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/visit': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/search': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/about': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/initesdata': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/visitor': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/visitLog': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/type': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/tag': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            },
            '/init': {
                target: 'http://localhost:8085',
                changeOrigin: true,
                secure: false
            }
        }
    },
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "window.jQuery": "jquery",
                "window.$": "jquery",
                Popper: ["popper.js", "default"]
            })
        ]
    }
}