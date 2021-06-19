require:
	cd ./api-parsons; composer install;
	cd ./angular-parsons; npm install


install:
	php ./api-parsons/bin/console doctrine:migrations:migrate;
	php ./api-parsons/bin/console doctrine:fixtures:load

server:
	cd ./api-parsons; symfony serve
ui:
	cd ./angular-parsons; npm start
