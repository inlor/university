let db = require('./../models/')

exports.dashboard = async (req, res) => {
    let _memos= await db.memo.findAll({
        where:{
            owner: req.session.user.id
        },
        include:[
            db.color, 
        ]
    }).then(e => {return e})
    let share= await db.memo.findAll({
        include:[
            db.color, 
            {
                model: db.share,
                where:{
                    userId: req.session.user.id
                }
            }
        ]
    }).then(e => {return e})
    res.render('memo/dashboard', {memos:_memos, share:share})
}

exports.create = async (req, res) => {
    const colors = await db.color.getColors()
    res.render('memo/form', {colors:colors, memo : db.memo.build()})
}

exports.store = async (req, res) => {
    let input = req.body
    const selected_color = await db.color.getColor(input.color)
    const _memo_id = await db.memo.create({ 
        content: input.content,
        owner: req.session.user.id,
        colorId: selected_color ? selected_color.id : 9,
        created: Date.now(),
        updated: Date.now(),
    }).then(e=>{return e.id})
    db.share.createShares(_memo_id, JSON.parse(input.share), db)
    res.redirect('/dashboard')
}

exports.show = async(req, res) => {
    res.render('memo/show', {memo: await db.memo.getMemo(req.params.id, db.color)})
}

exports.edit = async(req, res) => {
    let _memo = await db.memo.getMemo(req.params.id, db.color)
    const colors = await db.color.getColors()
    res.render('memo/form', {memo:_memo, colors:colors})
}

exports.update = async (req, res) => {
    let input = req.body
    let color = await db.color.getColor(input.color)
    let memo = await db.memo.getMemo(req.params.id, db.color)
    memo.content = input.content
    memo.colorId = color.id
    memo.updated = Date.now()
    await memo.save()
    //db.share.updateShares(1, _memo_id, JSON.parse(input.share), db.user)
    res.redirect('/dashboard')
}

exports.delete = async(req, res) => {
    let _memo = await  db.memo.getMemo(req.params.id, db.color)
    await _memo.destroy()
    res.redirect('/dashboard')
}