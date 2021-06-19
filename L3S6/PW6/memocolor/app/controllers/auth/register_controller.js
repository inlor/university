const { check, validationResult } = require('express-validator')
let db = require('./../../models')

exports.show_form = (req, res) => {
    res.render('auth/sing-up')
}

exports.register = async (req, res) => {
    let input = req.body
    await check('username', 'Username need to contains 5 chars at less.').isLength({ min: 5 })
    await check('username', 'Username already in use.').custom(async (value, {req}) => await db.user.getUser(input.username) === null).run(req)
    await check('password', 'Password need to contains 5 chars at less.').isLength({ min: 5 }).run(req)
    await check('confirm_password', 'Password confirmation field must have the same value as the password field.')
        .exists().custom((value, { req }) => value === input.password).run(req)

    const result = validationResult(req);
    if (result.isEmpty()) {
        db.user.create({
            username: input.username,
            password: require('js-md5')(input.password)
        })
        res.redirect('sing-in')
    }else{
        res.render('auth/sing-up', { errors: result.array() })
    }
}

