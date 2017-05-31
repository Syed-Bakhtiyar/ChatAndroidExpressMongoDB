var mongoose = require('mongoose');

var dbUri = 'mongodb://localhost/ChattApp';

mongoose.connect(dbUri);

var userSchema = new mongoose.Schema({

    email : {type : String, unique:true},

    name : {type:String}, 

    password : {type:String},

    isOnline : {type : Boolean, default: true}
});

var chatSchema = new mongoose.Schema({

    myid : String,

    youruid: String,

    name : String,

    message: String,

    date : {type:Date , default : Date.now} 

});

// var users = mongoose.model('Users',userSchema);

// var chat = mongoose.model('Chats',chatSchema);

module.exports =  { users:userSchema,
                    chat:chatSchema,
                    mongoose:mongoose};