let UserSchema = require('./models');
let passport = require("passport");

class User
{
    registerUser(req,res)
    {
        UserSchema.register(new UserSchema({username:req.body.username,email:req.body.email,longitude:req.body.longitude,latitude:req.body.latitude,bloodType:req.body.bloodType,fullName:req.body.fullName,lastDateOfDonation:req.body.lastDateOfDonation,age:req.body.age,doner:req.body.doner,mobileNumber:req.body.mobileNumber}),req.body.password,function(err,user){
            if(err){
                console.log(err);
                return res.status(400).send({success:false,msg:'Failed to create a new account'});
            }
            else
            {
                passport.authenticate("local")(req,res,function(){
                    console.log(user);
                    return res.json({success: true, msg: 'Created a new user successfully!'});
                });
            }
            
         });

    }
    loginUser(req,res,next) 
    {
        passport.authenticate('local', function(err,user) {
                if(!user) 
                {
                    console.log("Failed to login!");
                    res.send({error:true,msg:'Failed to log you in check your username or password'});
                }
                if(user) 
                {
                    console.log("Logged in successfully!");
                    res.send({error:false,msg:user});
                    
                }
                
        })(req,res,next);
    }
    logoutUser(req,res)
    {
        req.logout();
        console.log("Logged OUt!");
        res.send("Logged out successfully!");
    }
    getUsers(req,res)
    {
        UserSchema.find({},function(err,users)
        {
            if(err)
            {
                res.send({error:true,msg:'Failed to lookup all users'});
            }
            else
            {
                let userMap = {};

                users.forEach(function(user){
                    userMap = user;
    
                });
                res.send(userMap);
            }
           

        });
    }

    getDoners(req,res)
    {
        UserSchema.find({doner:true},function(err,users)
        {
            if(err)
            {
                res.send({error:true,msg:'Failed to lookup all users'});
            }
            else
            {
                let userMap = [];

                users.forEach(function(user){
                    userMap.push(user);
    
                });
                res.send(userMap);
            }
           

        });
    }

}

 

module.exports = User;