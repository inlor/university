exports.middleware = (req, res, next) => {
    if (req.session.user) {
        next()
    } else {
        res.redirect('/error')
    }
}

exports.access = async (req, res, next) => {
    let db = require('./../models')
    let memo = await db.memo.getMemo(req.params.id, db.color)
    let share = await db.share.findOne({
        where: {
            userId: req.session.user.id,
            memoId: req.params.id
        }
    }).then(e => { return e })
    if (memo != undefined && memo.owner === req.session.user.id) {
        next()
        return;
    } else if (share != undefined) {
        let str = req.route.path
        if ((str.includes('show') && share.rights > 0) ||
            (str.includes('edit') && share.rights > 1)) {
            next()
            return;
        }
    }
    res.redirect('/error')
}

exports.is_auth = (app) => {
    app.use((req, res, next) => {
        res.locals.auth = req.session.user ? true : false
        next()
    })
}