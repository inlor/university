const colors = ['red', 'orange', 'yellow', 'green', 'blue', 'violet', 'black', 'grey', 'white'];
for (let i = 0; i < colors.length; i++) {
  colors[i] = {id: i+1, color: colors[i]}
}
module.exports = { // 
  up: (queryInterface, Sequelize) => {
    return queryInterface.bulkInsert('colors', colors);
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.bulkDelete('colors', null, {});
  }
};