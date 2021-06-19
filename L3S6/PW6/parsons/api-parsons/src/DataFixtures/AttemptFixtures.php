<?php

namespace App\DataFixtures;

use App\Entity\Attempt;
use App\Repository\ExerciseRepository;
use App\Repository\UserRepository;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\DataFixtures\DependentFixtureInterface;
use Doctrine\Persistence\ObjectManager;

class AttemptFixtures extends Fixture implements DependentFixtureInterface
{
    private $userRepository;
    private $exerciseRepository;
    private static $number = 10;

    public function __construct(UserRepository $userRepository, ExerciseRepository $exerciseRepository)
    {
        $this->userRepository = $userRepository;
        $this->exerciseRepository = $exerciseRepository;
    }

    public function load(ObjectManager $manager)
    {
        $users = $this->userRepository->findAll();
        $exercises = $this->exerciseRepository->findAll();
        for ($i = 0; $i < self::$number; $i++) {
            foreach ($users as $user){
                $attempt = new Attempt();
                $attempt->setUser($user)
                    ->setExercise($exercises[$i])
                    ->setAttempting(rand(1, 15))
                    ->setSolved(boolval(rand(0, 1)));
                $manager->persist($attempt);
            }
        }
        $manager->flush();
    }

    public function getDependencies()
    {
        return array(
            UserFixtures::class,
            CourseFixtures::class,
            ExerciseFixtures::class,
        );
    }
}
