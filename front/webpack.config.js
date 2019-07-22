const Path = require('path')
const CleanWebpackPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require("html-webpack-plugin");
const TerserPlugin = require('terser-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");

module.exports = (env, argv) => ({
    entry: './src/index.jsx',
    output: {
        path: Path.resolve('../src/main/resources/static/'),
        filename: 'js/bundle.js'
    },
    resolve: {
        modules: [
            Path.resolve('./node_modules'),
            Path.resolve('./src')
        ]
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /(node_modules)/,
                use: 'babel-loader'
            },
            {
                test: /\.css$/,
                use: [
                    argv.mode !== 'production' ? 'style-loader' : MiniCssExtractPlugin.loader,
                    'css-loader'
                ]
            },
            {
                test: /favicon\.(gif|png|jpe?g|ico|svg)$/,
                use: {
                    loader: 'file-loader',
                    options: {
                        name: '/[name].[ext]',
                    }
                }
            },
            {
                test: /(?<!favicon)\.(gif|png|jpe?g|ico|svg)$/,
                use: {
                    loader: 'file-loader',
                    options: {
                        name: '/img/[name].[ext]',
                    }
                }
            },
            {
                test: /\.(woff2?|eot|ttf)$/,
                use: {
                    loader: 'file-loader',
                    options: {
                        name: '/font/[name].[ext]',
                    }
                }
            }
        ]
    },
    optimization: {
        minimizer: [
            new TerserPlugin({
                parallel: true,
                terserOptions: {
                    ecma: 6,
                    output: {
                        comments: false
                    }
                }
            }),
            new OptimizeCSSAssetsPlugin({
                cssProcessor: require('cssnano'),
                cssProcessorPluginOptions: {
                    preset: [
                        'default',
                        {
                            discardComments: { 
                                removeAll: true 
                            }
                        }
                    ]
                }
            })
        ]
    },
    plugins: [
        new CleanWebpackPlugin(),
        new HtmlWebpackPlugin({
            template: "./src/index.html",
            filename: "index.html",
            inject: false,
            minify: argv.mode !== 'production' ? false: {
                collapseWhitespace: true,
                removeAttributeQuotes: true,
                removeComments : true
            }
        }),
        new MiniCssExtractPlugin({
            filename: "css/bundle.css"
        })
    ]
});
