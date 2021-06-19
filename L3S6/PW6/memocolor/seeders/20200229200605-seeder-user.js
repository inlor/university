const users = ['Mack', 'lulu', 'Jack', 'Alex', 'demon777', 'frost'];
for (let i = 0; i < users.length; i++) {
  users[i] = {username: users[i], password: require('js-md5')('psw')}
}
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.bulkInsert('users', users);
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.bulkDelete('users', null, {});
  }
};