var express = require('express');
var app = express();

app.get('/user', function(req, res){
	var users = [{id: '1', name: 'sandip pani'}];
	res.send(users);
});

app.get('/photo', function(req, res){
	var photo = 'here is the photo';
	res.send(photo);
});

app.listen(8064);