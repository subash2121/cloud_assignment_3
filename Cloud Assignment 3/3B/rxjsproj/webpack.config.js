var path = require('path');

module.exports = {
   entry: {
      app: './src/testrx.js'
   },
   output: {
      path: path.resolve(__dirname, 'dev'),
      filename: 'main_bundle.js'
   },
   mode:'development',
   module: {
      rules: [
         {
            test:/\.(js)$/,
            include: path.resolve(__dirname, 'src'),
            loader: 'babel-loader',
            // query: {
            //    presets: ['@babel/preset-env']
            // }
         }
      ]
   }
};