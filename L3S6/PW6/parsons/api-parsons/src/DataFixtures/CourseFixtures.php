<?php

namespace App\DataFixtures;

use App\Entity\Course;
use App\Repository\UserRepository;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\DataFixtures\DependentFixtureInterface;
use Doctrine\Persistence\ObjectManager;
use Faker;

class CourseFixtures extends Fixture implements DependentFixtureInterface
{
    private static $number = 10;

    private $userRepository;

    public function __construct(UserRepository $userRepository)
    {
        $this->userRepository = $userRepository;
    }

    public function load(ObjectManager $manager)
    {
        $users = $this->userRepository->findAll();
        $faker = Faker\Factory::create('fr_FR');
        for ($i = 0; $i< self::$number; $i++){
            $course = new Course();
            $course->setAuthor($users[$i%count($users)])
                ->setTitle($faker->sentences(1,true))
                ->setContent($faker->paragraphs(5,true));
            $manager->persist($course);
        }
        $manager->flush();
    }

    public function getDependencies()
    {
        return array(
            UserFixtures::class,
        );
    }
}
