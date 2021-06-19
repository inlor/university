'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('personal_memo', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      owner:{
        type: Sequelize.INTEGER,
        references: {
          model: 'users',
          key: 'id'
        },
      },
      color:{
        type: Sequelize.INTEGER,
        references: {
          model: 'colors',
          key: 'id'
        },
      },
      description: Sequelize.TEXT,
    });
  },

  down: (queryInterface, Sequelize) => {
      return queryInterface.dropTable('personal_memo');
  }
};
