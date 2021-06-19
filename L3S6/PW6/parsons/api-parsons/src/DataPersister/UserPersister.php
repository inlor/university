<?php

namespace App\DataPersister;

use ApiPlatform\Core\DataPersister\DataPersisterInterface;
use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class UserPersister implements DataPersisterInterface{

    private $passwordEncoder;
    private $manager;

    public function __construct(UserPasswordEncoderInterface $passwordEncoder, EntityManagerInterface $manager)
    {
        $this->passwordEncoder = $passwordEncoder;
        $this->manager = $manager;
    }

    public function supports($data): bool
    {
        return $data instanceof User;
    }

    public function persist($data)
    {
        $data->setPassword(
            $this->passwordEncoder->encodePassword(
                $data, $data->getPassword()
            )
        );
        $this->manager->persist($data);
        $this->manager->flush();
    }

    public function remove($data)
    {

    }
}

