let express = require('express')

let app = express()

// Middlewares
require('./app/middlewares/middlewares')(app, express)
// Routes 
require('./app/routes/routes')(app)

app.listen(3000, () => console.log('Listening on port localhost:3000!'))