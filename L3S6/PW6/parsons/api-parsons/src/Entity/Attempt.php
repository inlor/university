<?php

namespace App\Entity;

use ApiPlatform\Core\Annotation\ApiResource;
use ApiPlatform\Core\Annotation\ApiFilter;
use ApiPlatform\Core\Bridge\Doctrine\Orm\Filter\SearchFilter;
use Symfony\Component\Serializer\Annotation\Groups;
use App\Entity\Traits\TimeStampableTrait;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;


/**
 * @ORM\Entity(repositoryClass="App\Repository\AttemptRepository")
 * @ORM\HasLifecycleCallbacks()
 * @ApiFilter(SearchFilter::class, properties={"user": "exact", "exercise": "exact"})
 * @ApiResource(attributes={
 *     "normalization_context"={"groups"={"attempt"}},
 *     "denormalizationContext"={"groups"={"attempt"}}
 *     })
 * @UniqueEntity(
 *     fields={"exercise", "user"}
 * )
 */
class Attempt
{
    use TimeStampableTrait;
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     * @Groups({"attempt", "course", "exercise"})
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Exercise", inversedBy="attempts")
     * @ORM\JoinColumn(nullable=false)
     * @Groups({"attempt", "user"})
     */
    private $exercise;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\User", inversedBy="attempts")
     * @ORM\JoinColumn(nullable=false)
     * @Groups({"attempt", "course", "exercise"})
     */
    private $user;

    /**
     * @ORM\Column(type="integer")
     * @Groups({"attempt", "course", "exercise"})
     */
    private $attempting;

    /**
     * @ORM\Column(type="boolean")
     * @Groups({"attempt", "course", "exercise"})
     */
    private $solved;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getExercise(): ?Exercise
    {
        return $this->exercise;
    }

    public function setExercise(?Exercise $exercise): self
    {
        $this->exercise = $exercise;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getAttempting(): ?int
    {
        return $this->attempting;
    }

    public function setAttempting(int $attempting): self
    {
        $this->attempting = $attempting;

        return $this;
    }

    public function getSolved(): ?bool
    {
        return $this->solved;
    }

    public function setSolved(bool $solved): self
    {
        $this->solved = $solved;

        return $this;
    }
}
