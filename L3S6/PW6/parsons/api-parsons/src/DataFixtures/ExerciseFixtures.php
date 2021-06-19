<?php

namespace App\DataFixtures;

use App\Entity\Course;
use App\Entity\Exercise;
use App\Repository\CourseRepository;
use App\Repository\UserRepository;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\DataFixtures\DependentFixtureInterface;
use Doctrine\Persistence\ObjectManager;
use Symfony\Component\Finder\Finder;
use Faker;

class ExerciseFixtures extends Fixture implements DependentFixtureInterface
{
    private static $number = 20;
    private static $path = 'Parsons';
    private $courseRepository;
    private $parsons = [];

    public function __construct(CourseRepository $courseRepository)
    {
        $this->courseRepository = $courseRepository;
        $finder = new Finder();
        $finder->files()->in(__DIR__.'/'.self::$path);
        foreach ($finder as $file){
            array_push($this->parsons, $file->getContents());
        }
    }

    public function load(ObjectManager $manager)
    {
        $courses = $this->courseRepository->findAll();
        $faker = Faker\Factory::create('fr_FR');
        for ($i = 0; $i< self::$number; $i++){
            $exercise = new Exercise();
            $exercise->setCourse($courses[$i%count($courses)])
                ->setTitle($faker->sentences(1,true))
                ->setDescription($faker->paragraphs(3,true))
                ->setParson($this->parsons[$i%count($this->parsons)]);
            $manager->persist($exercise);
        }
        $manager->flush();
    }

    public function getDependencies()
    {
        return array(
            CourseFixtures::class,
        );
    }
}
