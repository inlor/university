<?php

namespace App\DataFixtures;

use App\Entity\Subscription;
use App\Repository\CourseRepository;
use App\Repository\UserRepository;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\DataFixtures\DependentFixtureInterface;
use Doctrine\Persistence\ObjectManager;

class SubscriptionFixtures extends Fixture implements DependentFixtureInterface
{
    private $userRepository;
    private $courseRepository;

    public function __construct(UserRepository $userRepository, CourseRepository $courseRepository)
    {
        $this->userRepository = $userRepository;
        $this->courseRepository = $courseRepository;
    }

    public function load(ObjectManager $manager)
    {
        $users = $this->userRepository->findAll();
        $courses = $this->courseRepository->findAll();

        foreach ($users as $user){
            for ($i = 0; $i< count($courses); $i++){
                $sub = new Subscription();
                $sub->setUser($user)
                    ->setCourse($courses[$i])
                    ->setActive(true);
                $manager->persist($sub);
            }
        }
        $manager->flush();
    }

    public function getDependencies()
    {
        return array(
            UserFixtures::class,
            CourseFixtures::class,
        );
    }
}
