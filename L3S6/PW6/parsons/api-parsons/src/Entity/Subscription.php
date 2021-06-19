<?php

namespace App\Entity;

use App\Entity\Traits\TimeStampableTrait;

use ApiPlatform\Core\Annotation\ApiResource;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use ApiPlatform\Core\Annotation\ApiFilter;
use ApiPlatform\Core\Bridge\Doctrine\Orm\Filter\SearchFilter;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * @ORM\Entity(repositoryClass="App\Repository\SubscriptionRepository")
 * @ORM\HasLifecycleCallbacks()
 * @ApiFilter(SearchFilter::class, properties={"user": "exact", "course": "exact"})
 * @ApiResource(attributes={
 *     "normalization_context"={"groups"={"subscription"}},
 *     "denormalizationContext"={"groups"={"subscription"}}
 *     })
 * @UniqueEntity(
 *     fields={"course", "user"}
 * )
 */
class Subscription
{
    use TimeStampableTrait;
    /**
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     * @Groups({"subscription", "course"})
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\User", inversedBy="subscriptions")
     * @ORM\JoinColumn(nullable=false, onDelete="CASCADE")
     * @Groups({"subscription"})
     */
    private $user;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Course", inversedBy="subscriptions")
     * @ORM\JoinColumn(nullable=false, onDelete="CASCADE")
     * @Groups("subscription")
     */
    private $course;

    /**
     * @ORM\Column(type="boolean")
     * @Groups({"subscription", "course"})
     */
    private $active;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getCourse(): ?Course
    {
        return $this->course;
    }

    public function setCourse(?Course $course): self
    {
        $this->course = $course;

        return $this;
    }

    public function getActive(): ?bool
    {
        return $this->active;
    }

    public function setActive(bool $active): self
    {
        $this->active = $active;

        return $this;
    }
}
