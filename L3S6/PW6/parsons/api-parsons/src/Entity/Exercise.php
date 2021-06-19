<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use ApiPlatform\Core\Annotation\ApiResource;
use Symfony\Component\Serializer\Annotation\Groups;
use App\Entity\Traits\TimeStampableTrait;

/**
 * @ORM\Entity(repositoryClass="App\Repository\ExerciseRepository")
 * @ORM\HasLifecycleCallbacks()
 * @ApiResource(attributes={
 *     "normalization_context"={"groups"={"exercise"}},
 *     "denormalizationContext"={"groups"={"exercise"}}
 *     })
 */
class Exercise
{
    use TimeStampableTrait;

    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     * @Groups({"course", "exercise", "attempt"})
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups({"course", "exercise", "attempt"})
     */
    private $title;

    /**
     * @ORM\Column(type="text")
     * @Groups({"course", "exercise", "attempt"})
     */
    private $description;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Course", inversedBy="exercises")
     * @ORM\JoinColumn(nullable=false, onDelete="CASCADE")
     * @Groups({"exercise", "attempt"})
     */
    private $course;

    /**
     * @ORM\Column(type="text")
     * @Groups({"course", "exercise", "attempt"})
     */
    private $parson;

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Attempt", mappedBy="exercise", orphanRemoval=true)
     * @Groups({"exercise", "course"})
     */
    private $attempts;

    public function __construct()
    {
        $this->attempts = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getCourse(): ?Course
    {
        return $this->course;
    }

    public function setCourse(?Course $course): self
    {
        $this->course = $course;

        return $this;
    }

    public function getParson(): ?string
    {
        return $this->parson;
    }

    public function setParson(string $parson): self
    {
        $this->parson = $parson;

        return $this;
    }

    /**
     * @return Collection|Attempt[]
     */
    public function getAttempts(): Collection
    {
        return $this->attempts;
    }

    public function addAttempt(Attempt $attempt): self
    {
        if (!$this->attempts->contains($attempt)) {
            $this->attempts[] = $attempt;
            $attempt->setExercise($this);
        }

        return $this;
    }

    public function removeAttempt(Attempt $attempt): self
    {
        if ($this->attempts->contains($attempt)) {
            $this->attempts->removeElement($attempt);
            // set the owning side to null (unless already changed)
            if ($attempt->getExercise() === $this) {
                $attempt->setExercise(null);
            }
        }

        return $this;
    }
}
