package ru.vood.responce.common

import org.springframework.scheduling.support.PeriodicTrigger


class RandomizedDelayTrigger : PeriodicTrigger(1000)