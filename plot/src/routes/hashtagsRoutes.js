'use strict';

var cors = require('cors'),
    express = require('express'),
    app = express(),
    hashtagsRouter = express.Router(),
    configmongo = require('../config/configmongo.js'),
    MongoClient = require('mongodb').MongoClient;

console.log(configmongo.url);

var getHashtags = function ( ) {
    
    hashtagsRouter.all('*', cors());
    
    hashtagsRouter.route('/')
        .get(function (req, res) {
            MongoClient.connect(configmongo.url, function (err, db) {
                if (err) {
                    console.log(err);
                } else {
                    
                    var collection = db.collection('hashtags');

                    collection.find({}, {fields: {_id: 0}}).toArray(function (err, results) {

                    if (err) {
                        console.error(err);
                        res.statusCode = 500;
                        res.send({
                            result: 'error',
                            err:    err.code
                        });
                    }
                        res.send(results);
                    });
                };

            });

        });
    

    return hashtagsRouter;
        
};

module.exports = {
  getHashtags: getHashtags
};
                         
                         
