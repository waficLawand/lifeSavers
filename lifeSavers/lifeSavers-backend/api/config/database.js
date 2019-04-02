let mongoose = require('mongoose');

let setUpDatabase = ()=>
{
    mongoose.connect('mongodb://localhost/taskIt',(err)=>{
        if(err)
        {
            console.log("Error occured while setting up the database!");
        } else
        {
            console.log("MongoDb running!");
        }
    });

}

module.exports = setUpDatabase;