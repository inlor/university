let home_controller = require('./../controllers/home_controller')
let register_controller = require('./../controllers/auth/register_controller')
let login_controller = require('./../controllers/auth/login_controller')
let memo_controller = require('./../controllers/memo_controller')
let user_controller = require('./../controllers/user_controller')

let auth_mid = require('./../middlewares/authenticate')
let guest_mid = require('./../middlewares/guest')

module.exports = function (app){

    // Home
    app.get('/','home', guest_mid.middleware, home_controller.index)

    // Register
    app.get('/sing-up', 'sing-up', guest_mid.middleware, register_controller.show_form)

    app.post('/sing-up', guest_mid.middleware, register_controller.register)

    // Login
    app.get('/sing-in', 'sing-in', guest_mid.middleware, login_controller.show_form)

    app.post('/sing-in', guest_mid.middleware, login_controller.login)

    app.post('/logout', 'logout', login_controller.logout)

    // Memo CRUD
    app.get('/dashboard', 'dashboard', auth_mid.middleware, memo_controller.dashboard)

    app.get('/create', 'create', auth_mid.middleware, memo_controller.create)

    app.post('/create', auth_mid.middleware, memo_controller.store)

    app.get('/edit/:id', 'edit', auth_mid.middleware, auth_mid.access, memo_controller.edit)

    app.post('/edit/:id', auth_mid.middleware, auth_mid.access, memo_controller.update)

    app.get('/show/:id', 'show', auth_mid.middleware, auth_mid.access, memo_controller.show)

    app.post('/delete/:id', 'delete', auth_mid.middleware, auth_mid.access, memo_controller.delete)

    app.get('/api/users/', user_controller.get_users_json)

    app.get('/error', 'error', home_controller.error)

}