<?php


namespace App\EventListener;

use Lexik\Bundle\JWTAuthenticationBundle\Event\JWTCreatedEvent;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\Security\Core\User\UserInterface;

class JWTCreatedListener
{
    /**
     * @var RequestStack
     */
    private $requestStack;

    /**
     * @param RequestStack $requestStack
     */
    public function __construct(RequestStack $requestStack)
    {
        $this->requestStack = $requestStack;
    }

    /**
     * @param JWTCreatedEvent $event
     *
     * @return void
     */
    public function onJWTCreated(JWTCreatedEvent $event)
    {

        $user = $event->getUser();

        if (!$user instanceof UserInterface) {
            return;
        }

        $payload = $event->getData();
        $payload['id'] = $user->getId();

        $event->setData($payload);

        $header = $event->getHeader();
        $header['id'] = 'JWT';

        $event->setHeader($header);
    }
}