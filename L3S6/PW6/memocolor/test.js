let db = require('./app/models/')

db.memo.findAll({
    where:{
        owner: 1
    },
    include:[
        db.color, 
    ]
}).then(e => {console.log("OK", e)})

