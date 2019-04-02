
let  express  = require('express');
let UserController = require('./controller');


let User = new UserController();

let app = new express();

app.post('/newUser',User.registerUser);
app.post('/login',User.loginUser);
app.get('/logout',User.logoutUser);
app.get('/users',User.getUsers);


module.exports = app;
