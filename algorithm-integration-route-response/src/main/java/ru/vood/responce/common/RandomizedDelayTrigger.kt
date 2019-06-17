package ru.vood.responce.common

import org.springframework.scheduling.support.PeriodicTrigger
import org.springframework.stereotype.Component

@Component
class RandomizedDelayTrigger : PeriodicTrigger(1000)