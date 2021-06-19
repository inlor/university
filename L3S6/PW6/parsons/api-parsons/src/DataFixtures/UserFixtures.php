<?php

namespace App\DataFixtures;

use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\Persistence\ObjectManager;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use App\Entity\User;
use Faker;

class UserFixtures extends Fixture
{
    private static $password = '123456';
    private static $number = 60;

    private static $student = 'ROLE_STUDENT';
    private static $teacher = 'ROLE_TEACHER';

    private $encoder;

    public function __construct(UserPasswordEncoderInterface $encoder)
    {
        $this->encoder = $encoder;
    }
    public function load(ObjectManager $manager)
    {
        $faker = Faker\Factory::create('fr_FR');
        for ($i = 0; $i< self::$number; $i++){
            $user = new User();
            $user->setUsername($faker->name)
                ->setEmail($faker->email)
                ->setPassword($this->encoder->encodePassword($user, self::$password))
                ->setRoles($i<(self::$number/6) ? [self::$teacher] : [self::$student]);
            $manager->persist($user);
        }
        $manager->flush();
    }
}
