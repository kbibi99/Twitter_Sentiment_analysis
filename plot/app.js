'use strict';

var express = require('express');
var app = express();

var hashtagsRouter = require('./src/routes/hashtagsRoutes');

var port = process.env.PORT || 5000;


//used by express first
app.use(express.static('./public'));
app.use(express.static('./src'));



//templating engine
app.set('views', './src/views');      
app.set('view engine', 'ejs');


app.use('/hashtags', hashtagsRouter.getHashtags());

app.get('/', function (req, res) {
    res.render('index', {
        title: 'Hashtags Chart'
    });
});


app.listen(port, function () {
    console.log('running server on port ' + port) 
});
