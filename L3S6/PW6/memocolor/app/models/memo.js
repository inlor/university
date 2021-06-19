'use strict';
module.exports = (sequelize, Sequelize) => {
	const memo = sequelize.define('memo', {
		content: Sequelize.TEXT,
		owner: {
			type: Sequelize.INTEGER,
			references: {
				model: 'users',
				key: 'id'
			},
		},
		colorId: {
			type: Sequelize.INTEGER,
			references: {
				model: 'colors',
				key: 'id'
			},
		},
		created: Sequelize.DATE,
		updated: Sequelize.DATE,
	});

	memo.getMemo = async (id, color) => {
		return await memo.findOne({
			where: {
				id: id,
			},
			include:[color]
		}).then(e => { return e })
	}

	memo.associate = function (models) {
		memo.belongsTo(models.color, {foreignKey:"colorId"})
		memo.hasMany(models.share, { onDelete: 'cascade'})
	}
	return memo;
}