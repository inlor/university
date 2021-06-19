'use strict';
module.exports = (sequelize, Sequelize) => {
    const share = sequelize.define('share', {
        userId: Sequelize.INTEGER,
        rights: Sequelize.INTEGER,
        memoId: Sequelize.INTEGER,
    }, {});
    share.associate = function (models) {
        share.belongsTo(models.memo, {foreignKey:"memoId"})
    }
    share.createShares = async function(id, _share, db){
        console.log("azeaze", _share)
        for(let i=0; i<_share.length; i++){
            const _viewer = await db.user.getUser(_share[i].split(':')[0])
            if(_viewer!=undefined){
                share.create({
                    memoId:id,
                    userId:_viewer.id,
                    rights:_share[i].split(':')[1], 
                })
            }
        }
    }

    share.updateShares = async function(id, _share, db){
        
    }
    return share;
}