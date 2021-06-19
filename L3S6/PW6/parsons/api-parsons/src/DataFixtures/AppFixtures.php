<?php

namespace App\DataFixtures;

use App\Repository\UserRepository;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\DataFixtures\DependentFixtureInterface;
use Doctrine\Common\Persistence\ObjectManager;

class AppFixtures extends Fixture implements DependentFixtureInterface
{
    private $userRepository;
    private $student = 'student@parsons.com';
    private $teacher = 'teacher@parsons.com';

    public function __construct(UserRepository $userRepository)
    {
        $this->userRepository = $userRepository;
    }

    public function load(ObjectManager $manager)
    {
        $users = $this->userRepository->findAll();

        $teacher = $users[0];
        $student = $users[count($users)-1];

        $teacher->setEmail($this->teacher);
        $student->setEmail($this->student);

        $manager->persist($teacher);
        $manager->persist($student);

        $manager->flush();
    }

    public function getDependencies()
    {
        return array(
            UserFixtures::class,
        );
    }
}
