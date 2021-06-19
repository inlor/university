const { check, validationResult } = require('express-validator')
let db = require('./../../models')

exports.show_form = (req, res) => {
    res.render('auth/sing-in')
}

exports.login = async (req, res) => {
    let input = req.body
    req.session.user = await db.user.findOne({
        where: {
            username: input.username,
            password: require('js-md5')(input.password)
        }
    }).then((e) => {
        return e
    })
    if(req.session.user){
        res.redirect('/dashboard')
    }else{
        res.render('auth/sing-in', {errors: true})
    }
}

exports.logout = (req, res) => {
    req.session.user = undefined
    res.redirect('/')
}

