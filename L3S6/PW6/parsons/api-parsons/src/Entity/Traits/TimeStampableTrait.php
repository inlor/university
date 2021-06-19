<?php

namespace App\Entity\Traits;

use Symfony\Component\Serializer\Annotation\Groups;

use DateTime;
use DateTimeInterface;
use Exception;

/**
 * Trait TimeStampableTrait
 * @package App\Entity\Traits
 */
trait TimeStampableTrait
{
    /**
     * @ORM\Column(type="datetime")
     * @Groups({"user","course","attempt","exercise"})
     */
    private $createdAt;

    /**
     * @ORM\Column(type="datetime")
     * @Groups({"user","course","attempt","exercise"})
     */
    private $updatedAt;

    /**
     * @return DateTimeInterface|null
     * @throws Exception
     */
    public function getCreatedAt(): ?DateTimeInterface
    {
        return $this->createdAt ?? new DateTime();
    }

    /**
     * @param DateTimeInterface $createdAt
     * @return $this
     */
    public function setCreatedAt(DateTimeInterface $createdAt): self
    {
        $this->createdAt = $createdAt;

        return $this;
    }

    /**
     * @return DateTimeInterface|null
     */
    public function getUpdatedAt(): ?DateTimeInterface
    {
        return $this->updatedAt ?? new DateTime();
    }

    /**
     * @param DateTimeInterface $updatedAt
     * @return $this
     */
    public function setUpdatedAt(DateTimeInterface $updatedAt): self
    {
        $this->updatedAt = $updatedAt;

        return $this;
    }

    /**
     * @ORM\PrePersist
     * @ORM\PreUpdate
     */
    public function updateTimestamps(): void
    {
        $now = new DateTime();
        $this->setUpdatedAt($now);
        if ($this->getId() === null) {
            $this->setCreatedAt($now);
        }
    }
}