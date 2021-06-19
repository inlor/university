'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('memos', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      owner: {
        type: Sequelize.INTEGER,
        references: {
          model: 'users',
          key: 'id',
          onUpdate: 'CASCADE',
          onDelete: 'CASCADE',
        },
      },
      content: Sequelize.TEXT,
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
  },

  down: (queryInterface, Sequelize) => {

      return queryInterface.dropTable('memos');
  }
};
