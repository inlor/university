'use strict';
module.exports = (sequelize, Sequelize) => {
  const user = sequelize.define('user', {
    username: Sequelize.STRING,
    password: Sequelize.STRING,

  }, {});
  user.associate = function (models) {
    
  };
  user.getUser = async function(username){
    return await user.findOne({where:{username:username}}).then(e=>{return e}).catch(r=> {console.log("erro",r)})
  }
  return user;
};