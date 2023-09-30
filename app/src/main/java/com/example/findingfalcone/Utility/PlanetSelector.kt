package com.example.findingfalcone.Utility

import com.example.findingfalcone.Model.Planet

interface PlanetSelector {
    fun select(planet: Planet)
}