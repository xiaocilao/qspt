'use strict'
const path = require('path')
const webpack = require('webpack')

module.exports = {
    publicPath: '/qishui/base/',
    devServer: {
        proxy: {
            '/api/': {
                target: 'http://localhost:8082',   //代理接口
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''    //代理的路径
                }
            }
        }
    },

    outputDir: path.resolve(__dirname,'./qishui/base/')
}