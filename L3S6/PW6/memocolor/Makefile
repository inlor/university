run:
	npm run start

sass: 
	node_modules/node-sass/bin/node-sass -w --output-style compressed public/scss/ -o public/css/

migrate:
	npx sequelize db:migrate

seeder:
	npx sequelize db:seed:all

install:
	make migrate; make seeder;

install-database-ufr:
	mysql -h lampe -u etu21700877 -p --password=etu21700877 < pw6_memocolor.sql
