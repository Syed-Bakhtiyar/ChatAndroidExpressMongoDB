var app = require('./Chatting/Chatting');
var users = require('./UserSchema/users');

var sender = users.mongoose.model('Users',users.users);

var chat = users.mongoose.model('Chats',users.chat);

/*


    Create User


*/
app.post('/create',function(req,res){


console.log(req.body.email+" "+req.body.name+" "+req.body.password);

    var data = users.mongoose.models.Users({

       email : req.body.email,

        name : req.body.name, 

        password : req.body.password



});

data.save(function(err, userss){

    if(!err){

        res.json({check: true});

    }
    else{

        res.json({check: false});
    }


});
// res.json(data);


});

/**
 

 for send messages
 */

app.post('/chat',function(req,res){

    console.log('called '+req.body.message);

    var data =  users.mongoose.models.Chats({

    myid : req.body.myid,

    youruid: req.body.yourid,

    name : req.body.name,

    message : req.body.message, 

     

    });

    data.save(function(errs,chats){

            if(!errs){

                // res.json({send : true});
            }
            else{

                // res.json({send:false});
            }


    });


 var yourdata =  users.mongoose.models.Chats({

    myid : req.body.yourid,

    youruid: req.body.myid,

    name : req.body.name,

    message : req.body.message, 

     

    });

 yourdata.save(function(errs,chats){

            if(!errs){

                res.json({send : true});
            }
            else{

                res.json({send:false});
            }


    });

});
/**
 
For recieve messages

 */

app.get("/message/:mid/:uid",function(req,res){

    chat.find({myid:req.params.mid, youruid :req.params.uid},function(err, chat){

            if(!err){

                    res.json(chat);

            }



    });


});




/*

    User Login

*/


app.post('/login',function(req,res){

        console.log(""+req.body.email+" "+req.body.password);
        sender.find({email: req.body.email, password : req.body.password},function(err,users){

            if(!err){


                    console.log();
                    res.json({us : users});

            }
            // else{

            //         res.json({validation : 'not valid'});
            // }                



        });


});


/**
 
        all users

 */


app.get('/users',function(req,res){

        sender.find({},function(err,users){

            if(!err){

                    res.json(users);

            }



        });


});


app.listen(3000,function(){

    console.log('app start on port 3000');

});