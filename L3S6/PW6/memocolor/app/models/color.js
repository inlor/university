'use strict';
module.exports = (sequelize, Sequelize) => {
    const color = sequelize.define('color', {
        color: Sequelize.STRING,
    }, {})
    color.associate = function (models) {
        color.hasMany(models.memo)
    }

    color.getColor = async function (_color) {
        return await color.findOne({ where: { color: _color } }).then(e => { return e }).catch()
    }
    color.getColors = async function () {
        return await color.findAll().then((elements) => { return elements })
    }
    return color;
}