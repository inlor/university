<?php

namespace App\Entity;

use App\Entity\Traits\TimeStampableTrait;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use ApiPlatform\Core\Annotation\ApiResource;
use ApiPlatform\Core\Annotation\ApiFilter;
use ApiPlatform\Core\Bridge\Doctrine\Orm\Filter\SearchFilter;


/**
 * @ORM\Entity(repositoryClass="App\Repository\CourseRepository")
 * @ORM\HasLifecycleCallbacks()
 * @ApiFilter(SearchFilter::class, properties={"author": "exact"})
 * @ApiResource(attributes={
 *     "normalization_context"={"groups"={"course"}},
 *     "denormalizationContext"={"groups"={"course"}}
 *     })
 */
class Course
{
    use TimeStampableTrait;
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     * @Groups({"course", "user", "exercise", "subscription"})
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups({"course", "user", "exercise", "subscription"})
     */
    private $title;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\User", inversedBy="courses")
     * @ORM\JoinColumn(nullable=false, onDelete="CASCADE")
     * @Groups({"course", "subscription", "exercise"})
     */
    private $author;

    /**
     * @ORM\Column(type="text")
     * @Groups({"course", "user", "exercise", "subscription"})
     */
    private $content;

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Exercise", mappedBy="course", orphanRemoval=true)
     * @Groups({"course", "user", "subscription"})
     */
    private $exercises;

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Subscription", mappedBy="user", orphanRemoval=true)
     * @ORM\JoinColumn(onDelete="CASCADE")
     * @Groups({"course"})
     */
    private $subscriptions;

    public function __construct()
    {
        $this->exercises = new ArrayCollection();
        $this->subscriptions = new ArrayCollection();
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

    public function getAuthor(): ?User
    {
        return $this->author;
    }

    public function setAuthor(?User $author): self
    {
        $this->author = $author;

        return $this;
    }

    public function getContent(): ?string
    {
        return $this->content;
    }

    public function setContent(string $content): self
    {
        $this->content = $content;

        return $this;
    }

    /**
     * @return Collection|Exercise[]
     */
    public function getExercises(): Collection
    {
        return $this->exercises;
    }

    public function addExercise(Exercise $exercise): self
    {
        if (!$this->exercises->contains($exercise)) {
            $this->exercises[] = $exercise;
            $exercise->setCourse($this);
        }

        return $this;
    }

    public function removeExercise(Exercise $exercise): self
    {
        if ($this->exercises->contains($exercise)) {
            $this->exercises->removeElement($exercise);
            // set the owning side to null (unless already changed)
            if ($exercise->getCourse() === $this) {
                $exercise->setCourse(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Subscription[]
     */
    public function getSubscriptions(): Collection
    {
        return $this->subscriptions;
    }

    public function addSubscription(Subscription $subscription): self
    {
        if (!$this->subscriptions->contains($subscription)) {
            $this->subscriptions[] = $subscription;
            $subscription->setCourse($this);
        }

        return $this;
    }

    public function removeSubscription(Subscription $subscription): self
    {
        if ($this->subscriptions->contains($subscription)) {
            $this->subscriptions->removeElement($subscription);
            // set the owning side to null (unless already changed)
            if ($subscription->getUser() === $this) {
                $subscription->setUser(null);
            }
        }

        return $this;
    }
}
