'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('shares', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      userId: {
        type: Sequelize.INTEGER,
        references: {
          model: 'users',
          key: 'id',
          onUpdate: 'CASCADE',
          onDelete: 'CASCADE',
        },
      },
      memoId: {
        type: Sequelize.INTEGER,
        references: {
          model: 'memos',
          key: 'id',
          onUpdate: 'CASCADE',
          onDelete: 'CASCADE',
        },
        
      },
      rights: Sequelize.INTEGER,
    });
  },

  down: (queryInterface, Sequelize) => {

      return queryInterface.dropTable('shares');
  }
};
