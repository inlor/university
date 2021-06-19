exports.middleware = (req, res, next) => {
    if(!req.session.user){
        next()
    }else if(req.session.user){
        res.redirect('/dashboard')
    }else{
        res.redirect('/error')
    }
}