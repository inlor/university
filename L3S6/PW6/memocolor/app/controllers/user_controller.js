let db = require('./../models/')

exports.get_users_json = async(req, res) =>{
    let json = await db.user.findAll({
        attributes: ['username']
    }).then((e) => {return e}) 
    res.json(json)
}