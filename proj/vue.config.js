'use strict'
const path = require('path')
const webpack = require('webpack')

module.exports = {
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
    }
}