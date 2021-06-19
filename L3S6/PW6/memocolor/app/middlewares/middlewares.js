module.exports = function(app, express) {
    body_parser(app)
    sessions(app)
    named_routes(app)
    general(app, express)
    require('./authenticate').is_auth(app)
}

let body_parser = (app) =>{
    let bodyParser = require('body-parser')
    app.use(bodyParser.urlencoded({extended:false}))
    app.use(bodyParser.json())
}

let sessions = (app) =>{
    let session = require('express-session')
    app.use(session({
        secret: '3416ce6f81c28b41ba72fb39d6368fb9',
        resave: false,
        saveUninitialized: false,
        cookie: { secure: false }
    }))
}

let general = (app, express) =>{
    app.use('/assets', express.static('public'))
    app.set('view engine', 'ejs')
}

let named_routes = (app) =>{
    var Router = require('named-routes')
    var router = new Router()
    router.extendExpress(app)
    router.registerAppHelpers(app)
}