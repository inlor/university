const LoremIpsum = require("lorem-ipsum").LoremIpsum;
const lorem = new LoremIpsum({
  sentencesPerParagraph: {
    max: 8,
    min: 4
  },
  wordsPerSentence: {
    max: 16,
    min: 4
  }
});
let memos = [];
for (let i = 0; i < 5; i++) {
  let date = new Date().toISOString().slice(0, 19).replace('T', ' ')
  memos[i] = {owner: i+1, content: lorem.generateParagraphs(i+2), colorId: i+1, created: date, updated: date}
}
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.bulkInsert('memos', memos); 
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.bulkDelete('memos', null, {});
  }
};